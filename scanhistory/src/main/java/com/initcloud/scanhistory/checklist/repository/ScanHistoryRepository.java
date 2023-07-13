package com.initcloud.scanhistory.checklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistoryEntity, Long> {
	List<ScanHistoryEntity> findByFileHash(String fileHash);

	List<ScanHistoryEntity> findTop10ByOrderByIdDesc();
}
