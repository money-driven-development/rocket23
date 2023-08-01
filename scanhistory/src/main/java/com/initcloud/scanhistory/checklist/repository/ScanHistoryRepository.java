package com.initcloud.scanhistory.checklist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

	List<ScanHistory> findTop10ByTeamIdAndProjectIdOrderByHistoryIdDesc(Long teamId, Long projectId);

	Page<ScanHistory> findAllByTeamIdAndProjectId(Long teamId, Long projectId, Pageable page);

	List<ScanHistory> findAllByTeamIdOrderByHistoryIdDesc(Long teamId, Pageable page);

	List<ScanHistory> findByTeamIdAndHistoryIdLessThanOrderByHistoryIdDesc(Long teamId, Long historyId, Pageable page);

	Boolean existsByTeamIdAndHistoryIdLessThan(Long teamId, Long historyId);
}
