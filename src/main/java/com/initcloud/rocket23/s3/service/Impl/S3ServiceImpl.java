package com.initcloud.rocket23.s3.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.service.FileService;
import com.initcloud.rocket23.s3.service.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

	private final AmazonS3Client amazonS3Client;
	private final FileService fileService;

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new ApiException(ResponseCode.DATA_MISSING);
			}
			String fileName = file.getOriginalFilename();
			String bucket = "rocket23";
			String filePath = "https://" + bucket + "/" + fileName;
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(file.getContentType());
			objectMetadata.setContentLength(file.getSize());
			amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
			fileService.save(file, bucket, filePath);
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}
}
