package com.initcloud.rocket23.file.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void init(Path path);

	//Local 저장
	void store(MultipartFile file);

	void storeFile(MultipartFile file, Path path,boolean check) throws IOException;

	void unZip(MultipartFile file, Path path) throws IOException, IllegalArgumentException;

	//DB 저장
	void save(MultipartFile file, String type, String uploadPath);
}
