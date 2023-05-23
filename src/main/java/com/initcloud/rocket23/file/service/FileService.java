package com.initcloud.rocket23.file.service;

import com.initcloud.rocket23.file.dto.FileDto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
	void init();

	//Local 저장
	void store(MultipartFile file);

	//DB 저장
	void save(MultipartFile file, String type, String uploadPath);
}
