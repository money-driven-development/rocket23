package com.initcloud.rocket23.file.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.initcloud.rocket23.common.config.S3Config;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.dto.FileDto;
import com.initcloud.rocket23.file.entity.FileEntity;
import com.initcloud.rocket23.file.repository.FileRepository;
import com.initcloud.rocket23.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    private final AmazonS3Client amazonS3Client;
    private final S3Config s3Config;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new ApiException(ResponseCode.SERVER_CREATED_DIR_ERROR);
        }
    }

    /*
    로컬에 저장 및 DB 저장
     */
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
            save(file, "local");
        } catch (Exception e) {
            throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
        }
    }

    /*
        S3에 저장. 개발중
     */
    @Override
    public void storeS3(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new ApiException(ResponseCode.DATA_MISSING);
            }
            String fileName = file.getOriginalFilename();

        } catch (Exception e) {
            throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
        }
    }

    @Override
    public void save(MultipartFile file, String type) {

        String name = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String path = uploadPath;
        //S3인 경우 Server-type을 받기 위해 수정 필요.
        String serverType = type;

        fileRepository.save(FileDto.builder()
                .filename(name)
                .uuid(uuid)
                .path(path)
                .servertype(type)
                .build()
                .toEntity());
    }
}
