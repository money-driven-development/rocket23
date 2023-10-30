package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.entity.scanResult.CheckovScan;
import com.initcloud.rocket23.infra.repository.CheckovScanRepository;
import javax.persistence.RollbackException;
import javax.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScanSaveService {

    private final CheckovScanRepository checkovScanRepository;
    private final ScanParseService scanParseService;

    //@Transactional
    public CheckovScan saveScan(String data) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        //data = scanParseService.parseJSON(data);
        System.out.println(data);

        try {
            CheckovScan checkovScan = objectMapper.readValue(data, CheckovScan.class);
            try {
                CheckovScan savedScan = checkovScanRepository.save(checkovScan);
                return savedScan;
            } catch (TransactionalException e) {
                e.printStackTrace();
                throw e;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw e;
            } catch (RollbackException e) {
                e.printStackTrace();
                throw e;
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                e.printStackTrace(); // 예외 정보를 출력
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
