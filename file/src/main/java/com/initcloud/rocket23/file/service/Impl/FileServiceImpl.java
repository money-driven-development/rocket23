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

	@Override
	public void init() {
		try {
			Files.createDirectories(Paths.get(uploadPath));
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
			Path root = Paths.get(uploadPath);
			if (!Files.exists(root)) {
				init();
			}
			/// TODO: 2023-06-21 파일의 uuid펼 디렉토리를 생성하고, 해당 디렉토리에 파일 업로드 기능을 구현해야함.
			if (isZip(file)) {
				unZip(file, root);
			} else {
				storeFile(file, root);
			}
		} catch (IOException e) {
			throw new ApiException(ResponseCode.FILE_WRONG_ERROR);
		} catch(IllegalArgumentException e){
			throw new ApiException(ResponseCode.ZIP_ENCODING_ERROR);
		} catch(Exception e){
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	@Override
	public void storeFile(MultipartFile file, Path path) throws IOException {
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, path.resolve(file.getOriginalFilename()),
				StandardCopyOption.REPLACE_EXISTING);
		}
		save(file, "local", uploadPath);
	}

	@Override
	public void save(MultipartFile file, String type, String uploadPath) {

		String name = file.getOriginalFilename();

		String uuid = UUID.randomUUID().toString();

		FileEntity fileEntity = FileDto.toDto(name, uuid, uploadPath, type).toEntity();
		fileRepository.save(fileEntity);
	}

	@Override
	public boolean isZip(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		String check = "zip";
		/*
		TODO: 2023-06-20 zip확장자뿐만이 아니라 tar.gz과 같은 다른 확장자 고려
		 */
		return extension.matches(check);
	}

	@Override
	public void unZip(MultipartFile file, Path path) throws IOException, IllegalArgumentException {
		Charset CP866 = Charset.forName("CP866");
		try(ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream(),CP866)){
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while(zipEntry != null){
				boolean isDirectory = false;
				if(zipEntry.getName().endsWith(File.separator)){
					isDirectory = true;
				}
				Path newPath = zipSlipProtect(zipEntry, path);
				if(isDirectory){
					Files.createDirectories(newPath);
				}else{
					if(newPath.getParent() != null){
						if(Files.notExists(newPath.getParent())){
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

	private Path zipSlipProtect(ZipEntry zipEntry,Path path) {
		Path pathResolved = path.resolve(zipEntry.getName());
		Path normalizePath = pathResolved.normalize();
		if(!normalizePath.startsWith(path)){
			throw new ApiException(ResponseCode.ZIP_PATH_ERROR);
		}
		return normalizePath;
	}
}
