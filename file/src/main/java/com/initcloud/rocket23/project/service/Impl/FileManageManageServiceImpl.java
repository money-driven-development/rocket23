package com.initcloud.rocket23.project.service.Impl;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.project.dto.RedisFileDto;
import com.initcloud.rocket23.project.enums.ProjectType;
import com.initcloud.rocket23.project.service.FileManageService;
import com.initcloud.rocket23.infra.redis.pubsub.RedisMessagePublisher;

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
public class FileManageManageServiceImpl implements FileManageService {

	private final RedisMessagePublisher redisMessagePublisher;

	private String check = "zip";
	private Charset CP866 = Charset.forName("CP866");
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;

	/**
	 * @param path 파일 저장 root 경로
	 *             파일 저장 root 경로 디렉토리 생성
	 */
	@Override
	public void init(Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_CREATED_DIR_ERROR);
		}
	}

	/**
	 * @param file 업로드된 파일
	 *             uuid값을 통해 파일 별도의 디렉토리 이름 생성 -> 파일 저장 root 위치
	 */
	@Override
	public RedisFileDto store(MultipartFile file, String teamCode, String projectCode) {
		try {
			if (file.isEmpty()) {
				throw new ApiException(ResponseCode.DATA_MISSING);
			}

			String uuid = UUID.randomUUID().toString();
			Path root = Paths.get(uploadPath + "/" + uuid);

			if (!Files.exists(root)) {
				init(root);
			}

			boolean isZipFile = isZip(file);
			return storeFile(file, root, uuid, isZipFile, teamCode, projectCode);
		} catch (IOException e) {
			throw new ApiException(ResponseCode.FILE_WRONG_ERROR);
		} catch (IllegalArgumentException e) {
			throw new ApiException(ResponseCode.ZIP_ENCODING_ERROR);
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	/**
	 * @param file      업로드된 파일
	 * @param path      파일 저장 root 경로
	 * @param isZipFile zip파일인지 확인
	 * @throws IOException file.getinputstream의 경우 IOException이 발생
	 *                     파일에 대해 저장, zip파일 유무에 따라 unzip호출
	 *                                         todo file을 db에 저장할 떄 저장위치에 관해서 수정해야함.
	 */
	@Override
	public RedisFileDto storeFile(MultipartFile file, Path path, String uuid, boolean isZipFile, String teamCode, String projectCode) throws IOException {
		if (isZipFile) {
			unZip(file, path);
		} else {
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		return save(file, ProjectType.LOCAL, uuid, path.toString(), teamCode, projectCode);
	}

	/**
	 * @param file       업로드된 파일
	 * @param type       파일 저장 종류
	 * @param uploadPath 파일이 저장된 root 디렉토리 위치
	 *                   파일 정보를 repository에 저장
	 *                   저장 후 redis publish를 통한 uuid, timestamp 정보 메세지 생성
	 */
	@Override
	public RedisFileDto save(MultipartFile file, ProjectType type, String uuid, String uploadPath, String teamCode, String projectCode) {
		RedisFileDto dto = RedisFileDto.toDto(uuid, file.getOriginalFilename(), teamCode, projectCode);
		redisMessagePublisher.publishFileMessage(dto);
		return dto;
	}

	/**
	 * @param file 업로드된 파일
	 * @return zip파일 -> true, 아닌 경우 -> false
	 * <p>
	 * 파일 확장자를 통한 zip파일 확인
	 */
	private boolean isZip(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		/*
		TODO: 2023-06-20 zip확장자뿐만이 아니라 tar.gz과 같은 다른 확장자 고려
		 */
		return extension.matches(check);
	}

	/**
	 * @param file 업로드된 파일
	 * @param path 파일 저장 root 경로
	 * @throws IOException              file.getinputstream의 경우 IOException이 발생
	 * @throws IllegalArgumentException zipinputstream의 경우 encoding에서 해당 exception이 발생
	 *                                  <p>
	 *                                  zip파일을 순서별로 압축해제,
	 *                                  디렉터로인 경우 생성, 파일인 경우 복사
	 */
	@Override
	public void unZip(MultipartFile file, Path path) throws IOException, IllegalArgumentException {
		try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream(), CP866)) {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				Path newPath = path.resolve(zipEntry.getName());
				if (zipEntry.getName().endsWith(File.separator)) {
					Files.createDirectories(newPath);
				} else {
					if (newPath.getParent() != null && Files.notExists(newPath.getParent())) {
						Files.createDirectories(newPath.getParent());
					}
					Files.copy(zipInputStream, newPath, StandardCopyOption.REPLACE_EXISTING);
				}
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.closeEntry();
		}
	}
}
