package com.initcloud.rocket23.s3.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	void store(MultipartFile file);
}
