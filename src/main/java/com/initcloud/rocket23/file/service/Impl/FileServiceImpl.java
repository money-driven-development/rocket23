package com.initcloud.rocket23.file.service.Impl;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;

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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			}
			save(file, "local", uploadPath);
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	@Override
	public void save(MultipartFile file, String type, String uploadPath) {

		String name = file.getOriginalFilename();

		String uuid = UUID.randomUUID().toString();

		FileEntity fileEntity = FileDto.toDto(name, uuid, uploadPath, type).toEntity();
		fileRepository.save(fileEntity);
	}

}
