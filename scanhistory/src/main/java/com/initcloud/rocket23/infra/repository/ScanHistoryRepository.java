package com.initcloud.rocket23.infra.repository;

import java.util.List;
import java.util.Optional;

import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {
    Optional<ScanHistory> findTopByTeam_TeamCodeAndProject_ProjectCodeAndScanHashOrderById(String teamCode,
                                                                                           String projectCode,
                                                                                           String hashCode);

	Optional<ScanHistory> findTopByTeam_TeamCodeAndProject_ProjectCodeAndScanHashOrderByIdDesc(String teamCode,
																						   String projectCode,
																						   String hashCode);

	Page<ScanHistory> findAllByTeam_TeamCodeAndProject_ProjectCodeOrderByIdDesc(Pageable pageable, String teamCode,
		String projectCode);

	List<ScanHistory> findAllByTeam_TeamCodeAndProject_ProjectCodeOrderByIdDesc(String teamCode, String projectCode);

    List<ScanHistory> findAllByFileHash(String fileHash);

	ScanHistory findByFileHash(String fileHash);
}
