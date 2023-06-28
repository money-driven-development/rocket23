package com.initcloud.rocket23.s3.service.Impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.dto.FileDto;
import com.initcloud.rocket23.file.enums.ServerType;
import com.initcloud.rocket23.file.repository.FileRepository;
import com.initcloud.rocket23.s3.service.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

	private final AmazonS3Client amazonS3Client;
	private final FileRepository fileRepository;

	/**
	 *
	 * @param file 업로드된 파일
	 *  S3에 저장
	 */
	@Override
	public void store(MultipartFile file) {
		checkEmptyFile(file);
		try {
			String fileName = file.getOriginalFilename();
			String bucket = "rocket23";
			String uuid = UUID.randomUUID().toString();
			String filePath = "https://" + bucket + "/" + fileName;
			storeFileToAmazonS3(file, bucket, fileName);
			fileRepository.save(FileDto.toDto(fileName, uuid, filePath, ServerType.S3).toEntity());
		} catch (IOException e) {
			throw new ApiException(ResponseCode.AWS_FILE_UPLOAD_ERROR);
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	private void checkEmptyFile(MultipartFile file) {
		if (file.isEmpty()) {
			throw new ApiException(ResponseCode.FILE_EMPTY);
		}
	}

	private void storeFileToAmazonS3(MultipartFile file, String bucket, String fileName) throws IOException {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(file.getContentType());
		objectMetadata.setContentLength(file.getSize());
		amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
	}
}
