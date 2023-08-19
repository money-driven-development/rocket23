package com.initcloud.rocket23.project.service;

import com.initcloud.rocket23.project.enums.ServerType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    void init(Path path);

    //Local 저장
    void store(MultipartFile file);

    void storeFile(MultipartFile file, Path path, String uuid, boolean check) throws IOException;

    void unZip(MultipartFile file, Path path) throws IOException, IllegalArgumentException;

    //DB 저장
    void save(MultipartFile file, ServerType type, String uuid, String uploadPath);
}
