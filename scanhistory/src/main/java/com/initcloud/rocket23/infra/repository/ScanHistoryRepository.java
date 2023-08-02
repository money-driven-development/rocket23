package com.initcloud.rocket23.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.rocket23.checklist.entity.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {
	List<ScanHistory> findByFileHash(String fileHash);
}
