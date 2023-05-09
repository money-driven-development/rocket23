package com.initcloud.rocket23.file.service.Impl;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
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
            //파일 원본이름
            String name = file.getOriginalFilename();
            //파일 uuid
            String uuid = UUID.randomUUID().toString();
            //파일 저장소 경로
            String path = uploadPath;
            //서버-Type
            //String serverType =
            //파일 이름 저장
            fileRepository.save(FileEntity.builder()
                    .filename(name)
                    .uuid(uuid)
                    .path(path)
                    .build());

        } catch (Exception e) {
            throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
        }
    }
}
