package com.initcloud.scanhistory.checklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistoryEntity, Long> {
}
