package com.initcloud.rocket23.file.service.Impl;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    /*
    23.05.09
    @Value를 사용해서 Property의 값을 가져옴
    init() 새로운 dir 생성
     */
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new ApiException(ResponseCode.SERVER_CREATED_DIR_ERROR);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new ApiException(ResponseCode.DATA_MISSING);
            }
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
        }
    }
}
