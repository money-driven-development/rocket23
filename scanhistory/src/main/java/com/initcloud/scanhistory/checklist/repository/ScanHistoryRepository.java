package com.initcloud.scanhistory.checklist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

	List<ScanHistory> findTop10ByTeamIdOrderByHistorySeqDesc(String teamId);

	/* TODO: 페이지네이션과 관련된 기능으로 추후 활용예정입니다.(23.07.30)
	Optional<ScanHistory> findTop10ByFileHash(String fileHash);

	List<ScanHistory> findAllByOrderByHistorySeqDesc(Pageable page);

	List<ScanHistory> findByHistorySeqLessThanOrderByHistorySeqDesc(Long id, Pageable page);

	Boolean existsByHistorySeqLessThan(Long id);
	 */
}
