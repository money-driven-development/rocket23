package com.initcloud.rocket23.project.service;

import com.initcloud.rocket23.project.dto.RedisFileDto;
import com.initcloud.rocket23.project.enums.ServerType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileManageService {
    void init(Path path);

    //Local 저장
    RedisFileDto store(MultipartFile file, String teamCode, String projectCode);

    RedisFileDto storeFile(MultipartFile file, Path path, String uuid, boolean check, String teamCode, String projectCode) throws IOException;

    void unZip(MultipartFile file, Path path) throws IOException, IllegalArgumentException;

    //DB 저장
    RedisFileDto save(MultipartFile file, ServerType type, String uuid, String uploadPath, String teamCode, String projectCode);
}
