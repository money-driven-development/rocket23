package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.entity.scanResult.CheckovScan;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.CheckovScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScanSaveService {

    private final CheckovScanRepository checkovScanRepository;
    private final ScanParseService scanParseService;

    @Transactional
    public CheckovScan saveCheckovScan(String data) throws Exception {

        data = scanParseService.parseJSON(data);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        CheckovScan checkovScan = objectMapper.readValue(data, CheckovScan.class);

        try {
            return checkovScanRepository.save(checkovScan);
        } catch (Exception e) {
            throw new ApiException(ResponseCode.SCAN_SAVE_ERROR);
        }

    }

}
