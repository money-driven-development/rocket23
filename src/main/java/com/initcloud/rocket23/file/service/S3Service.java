package com.initcloud.rocket23.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	void storeS3(MultipartFile file);
}
