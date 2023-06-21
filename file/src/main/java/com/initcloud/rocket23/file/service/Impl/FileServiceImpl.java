package com.initcloud.rocket23.file.service.Impl;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.dto.FileDto;
import com.initcloud.rocket23.file.entity.FileEntity;
import com.initcloud.rocket23.file.repository.FileRepository;
import com.initcloud.rocket23.file.service.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
	private final FileRepository fileRepository;

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;

	//path입력을 통해서 uuid 디렉토리를 생성함.
	@Override
	public void init(Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_CREATED_DIR_ERROR);
		}
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new ApiException(ResponseCode.DATA_MISSING);
			}
			//기존의 파일 저장위치와 uuid 값을 결합하여, uuid별 디렉토리를 생성
			String uuid = UUID.randomUUID().toString();
			String url = uploadPath + uuid;
			Path root = Paths.get(url);
			boolean zipCheck = false;
			if (!Files.exists(root)) {
				init(root);
			}
			zipCheck = isZip(file);
			storeFile(file, root, zipCheck);
		} catch (IOException e) {
			throw new ApiException(ResponseCode.FILE_WRONG_ERROR);
		} catch (IllegalArgumentException e) {
			throw new ApiException(ResponseCode.ZIP_ENCODING_ERROR);
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	@Override
	public void storeFile(MultipartFile file, Path path, boolean check) throws IOException {
		if (!check) {
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			}
		} else {
			unZip(file, path);
		}
		save(file, "local", path.toString());
	}

	@Override
	public void save(MultipartFile file, String type, String uploadPath) {
		String name = file.getOriginalFilename();
		String uuid = UUID.randomUUID().toString();
		FileEntity fileEntity = FileDto.toDto(name, uuid, uploadPath, type).toEntity();
		fileRepository.save(fileEntity);
	}

	/**
	 *
	 * @param file 업로드된 파일
	 * @return zip파일 -> true, 아닌 경우 -> false
	 *
	 * 파일 확장자를 통한 zip파일 확인
	 */
	private boolean isZip(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		String check = "zip";
		/*
		TODO: 2023-06-20 zip확장자뿐만이 아니라 tar.gz과 같은 다른 확장자 고려
		 */
		return extension.matches(check);
	}

	/**
	 *
	 * @param file 업로드된 파일
	 * @param path 저장할 root 경로
	 * @throws IOException file.getinputstream의 경우 IOException이 발생
	 * @throws IllegalArgumentException zipinputstream의 경우 encoding에서 해당 exception이 발생
	 *
	 * zip파일을 순서별로 압축해제,
	 * 디렉터로인 경우 생성, 파일인 경우 복사
	 *
	 */
	@Override
	public void unZip(MultipartFile file, Path path) throws IOException, IllegalArgumentException {
		Charset CP866 = Charset.forName("CP866");
		try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream(), CP866)) {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				Path newPath = zipSlipProtect(zipEntry, path);
				if (zipEntry.getName().endsWith(File.separator)) {
					Files.createDirectories(newPath);
				} else {
					if (newPath.getParent() != null) {
						if (Files.notExists(newPath.getParent())) {
							Files.createDirectories(newPath.getParent());
						}
					}
					Files.copy(zipInputStream, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.closeEntry();
		}
	}

	/**
	 *
	 * @param zipEntry 저장하고자 하는 zip파일의 요소
	 * @param path 저장하고자하는 zip파일의 root 경로
	 * @return zipentry의 저장 경로
	 *
	 * zip 내부 파일에 대한 경로 반환,
	 * root가 올바르지 않은 경우, 오루 -> ZIP_PATH_ERROR 호출
	 */
	private Path zipSlipProtect(ZipEntry zipEntry, Path path) {
		Path pathResolved = path.resolve(zipEntry.getName());
		Path normalizePath = pathResolved.normalize();
		if (!normalizePath.startsWith(path)) {
			throw new ApiException(ResponseCode.ZIP_PATH_ERROR);
		}
		return normalizePath;
	}
}
