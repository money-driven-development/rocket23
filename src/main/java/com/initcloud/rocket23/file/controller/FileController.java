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


    //벤더추가
    @PostMapping(value = "/file")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) throws IllegalStateException, IOException {
        fileService.store(file);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
