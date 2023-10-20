package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.entity.CheckovScan;
import com.initcloud.rocket23.infra.repository.CheckovScanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScanSaveService {

    private final CheckovScanRepository checkovScanRepository;

    public void saveScan(String data) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        CheckovScan checkovScan = objectMapper.readValue(data, CheckovScan.class);

        checkovScanRepository.save(checkovScan);
    }

}
