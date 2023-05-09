package com.initcloud.rocket23.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
    void init();

    void store(MultipartFile file);

}
