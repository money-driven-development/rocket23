package com.initcloud.scanhistory.checklist.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {
	List<ScanHistory> findTop10ByFileHash(String fileHash);

	List<ScanHistory> findTop10ByOrderByIdDesc();

	List<ScanHistory> findAllByOrderByIdDesc(Pageable page);

	List<ScanHistory> findByIdLessThanOrderByIdDesc(Long id, Pageable page);

	Boolean existsByIdLessThan(Long id);
}
