package com.initcloud.scanhistory.checklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestPart;

import com.initcloud.scanhistory.checklist.entity.ScanHistoryDetailEntity;

@Repository
public interface ScanHistoryDetailRespository extends JpaRepository<ScanHistoryDetailEntity, Long> {
}
