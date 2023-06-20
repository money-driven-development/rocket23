package com.initcloud.rocket23.file.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void init();

	//Local 저장
	void store(MultipartFile file);

	boolean isZipfile(MultipartFile file) throws IOException;

	//DB 저장
	void save(MultipartFile file, String type, String uploadPath);
}
