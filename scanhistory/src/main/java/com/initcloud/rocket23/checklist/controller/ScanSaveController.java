package com.initcloud.rocket23.checklist.controller;

import com.initcloud.rocket23.checklist.entity.scanResult.CheckovScan;
import com.initcloud.rocket23.checklist.service.ScanSaveService;
import com.initcloud.rocket23.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Scan save API")
@RestController
@RequestMapping("/rocket/save/")
@RequiredArgsConstructor
public class ScanSaveController {

    private final ScanSaveService scanSaveService;

    @PostMapping
    public ResponseDto<CheckovScan> scanSave(@RequestBody String data) throws Exception {
        CheckovScan dto = scanSaveService.saveScan(data);
        return new ResponseDto<>(dto);
    }

}
