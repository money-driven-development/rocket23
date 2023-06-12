package com.initcloud.rocket23.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void init();

	//Local 저장
	void store(MultipartFile file);

	//DB 저장
	void save(MultipartFile file, String type, String uploadPath);
}
