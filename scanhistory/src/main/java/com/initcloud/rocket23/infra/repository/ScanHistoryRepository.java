package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.checklist.entity.ScanHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

	/**
	 * 파일 hash와 일치하는 상위 1개의 목록만을 조회합니다.
	 */
	ScanHistory findTopByTeamIdAndProjectIdAndFileHashOrderById(Long teamId, Long projectId, String fileHash);

	List<ScanHistory> findTop10ByTeamIdAndProjectIdOrderByIdDesc(Long teamId, Long projectId);

	Page<ScanHistory> findAllByTeamIdAndProjectId(Long teamId, Long projectId, Pageable page);

	List<ScanHistory> findAllByTeamIdOrderByIdDesc(Long teamId, Pageable page);

	List<ScanHistory> findByTeamIdAndIdLessThanOrderByIdDesc(Long teamId, Long Id, Pageable page);

	Boolean existsByTeamIdAndIdLessThan(Long teamId, Long historyId);
}
