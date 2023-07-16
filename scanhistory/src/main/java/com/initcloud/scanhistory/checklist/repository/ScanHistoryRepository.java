package com.initcloud.scanhistory.checklist.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistoryEntity, Long> {
	List<ScanHistoryEntity> findTop10ByFileHash(String fileHash);

	List<ScanHistoryEntity> findTop10ByOrderByIdDesc();

	List<ScanHistoryEntity> findAllByOrderByIdDesc(Pageable page);

	List<ScanHistoryEntity> findByIdLessThanOrderByIdDesc(Long id, Pageable page);

	Boolean existsByIdLessThan(Long id);
}
