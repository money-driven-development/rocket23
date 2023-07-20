package com.initcloud.scanhistory.checklist.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistoryEntity, Long> {
	List<ScanHistoryEntity> findTop10ByFileHash(String fileHash);

	List<ScanHistoryEntity> findTop10ByOrderByIdDesc();

	List<ScanHistoryEntity> findAllByOrderByIdDesc(Pageable page);

	List<ScanHistoryEntity> findAllByFileNameEndsWithIgnoreCaseByOrderByIdDesc(String fileType, Pageable page);

	List<ScanHistoryEntity> findAllByFileNameEndsWithIgnoreCaseByIdLessThanOrderByIdDesc(Long id, String fileType,
		Pageable page);

	List<ScanHistoryEntity> findByIdLessThanOrderByIdDesc(Long id, Pageable page);

	Boolean existsByIdLessThan(Long id);
}
