package com.initcloud.rocket23.file.controller;

import com.initcloud.rocket23.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class FileController {

    private final FileService firstService;
    @GetMapping("/first")
    public Map<String,Object> firstcontroller(){
        return firstService.getFirstData();
    }

    //벤더추가
    @PostMapping(value = "/file")
    public ResponseEntity<String> uploadFile(MultipartFile file) throws IllegalStateException, IOException{
        if(!file.isEmpty()){
            file.transferTo(new File(file.getOriginalFilename()));
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
