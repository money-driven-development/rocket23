package com.initcloud.rocket23.example.controller;

import com.initcloud.rocket23.example.service.ExampleService;
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
public class ExampleController {

    private final ExampleService exampleService;
    @GetMapping("/first")
    public Map<String,Object> firstcontroller(){
        return exampleService.getFirstData();
    }

    //벤더추가
}
