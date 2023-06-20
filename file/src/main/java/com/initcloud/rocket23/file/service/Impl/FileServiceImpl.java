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
			if (isZip(file)) {
				storeZip(file, root);
			} else {
				storeFile(file, root);
			}
		} catch (IOException e) {
			throw new ApiException(ResponseCode.FILE_WRONG);
		} catch (Exception e) {
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
		/// TODO: 2023-06-20 zip확장자뿐만이 아니라 tar.gz과 같은 다른 확장자 고려
		return extension.matches(check);
	}

	@Override
	public void storeZip(MultipartFile file, Path path) throws IOException {
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		while (zipEntry != null) {
			String zipFileName = zipEntry.getName();
			Path newPath = path.resolve(zipFileName);
			if (zipEntry.isDirectory()) {
				Files.createDirectories(path.resolve(newPath));
			} else {
				Files.copy(zipInputStream, newPath, StandardCopyOption.REPLACE_EXISTING);
			}
			zipEntry = zipInputStream.getNextEntry();
		}
	}
}
