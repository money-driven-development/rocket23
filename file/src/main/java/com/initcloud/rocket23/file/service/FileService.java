package com.initcloud.rocket23.file.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	void init();

	//Local 저장
	void store(MultipartFile file);

	boolean isZip(MultipartFile file);

	void storeFile(MultipartFile file, Path path) throws IOException;

	void storeZip(ZipInputStream zipInputStream, Path path) throws IOException;

	//DB 저장
	void save(MultipartFile file, String type, String uploadPath);
}
