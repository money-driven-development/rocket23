package com.initcloud.rocket23.file.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.service.S3Service;

import lombok.RequiredArgsConstructor;

// TODO: 2023-05-19 S3 service 개발 예정, 박병제
@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service {

	//    private final AmazonS3Client amazonS3Client;
	//    private final S3Config s3Config;

	//    @Value("${cloud.aws.s3.bucket}")
	//    private String bucket;
	@Override
	public void storeS3(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new ApiException(ResponseCode.DATA_MISSING);
			}
			String fileName = file.getOriginalFilename();

		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}
}
