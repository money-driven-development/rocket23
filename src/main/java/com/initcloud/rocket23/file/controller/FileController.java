package com.initcloud.rocket23.file.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;


    @PostMapping(value = "/file")
    public ResponseDto<?> uploadFile(@RequestPart("file") MultipartFile file) throws IllegalStateException, IOException {
        fileService.store(file);
        return new ResponseDto<>(null);
    }

    /*
    S3 저장 개발중
     */
    /*@PostMapping(value = "/file/s3")
    public ResponseDto<?> uploadFiletoS3(@RequestPart("file") MultipartFile file) {
        fileService.storeS3(file);
        return new ResponseDto<>(null);
    }*/
}
