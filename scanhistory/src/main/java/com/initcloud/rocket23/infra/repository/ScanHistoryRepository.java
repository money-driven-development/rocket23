package com.initcloud.rocket23.infra.repository;

import java.util.Optional;

import com.initcloud.rocket23.checklist.entity.ScanHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {
	Optional<ScanHistory> findTopByTeamIdAndProjectIdAndFileHashOrderById(Long teamId, Long projectId, String hashCode);
}
