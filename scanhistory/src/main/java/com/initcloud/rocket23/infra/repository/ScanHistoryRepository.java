package com.initcloud.rocket23.infra.repository;

import java.util.Optional;

import com.initcloud.rocket23.checklist.entity.ScanHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {
	Optional<ScanHistory> findTopByTeam_TeamCodeAndProject_ProjectCodeAndFileHashOrderById(String teamCode,
		String projectCode, String hashCode);

	Page<ScanHistory> findAllByTeam_TeamCodeAndProject_ProjectCode(Pageable pageable, String teamCode,
		String projectCode);
}
