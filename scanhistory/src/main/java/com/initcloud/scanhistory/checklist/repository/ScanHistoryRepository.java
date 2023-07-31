package com.initcloud.scanhistory.checklist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.ScanHistory;

@Repository
public interface ScanHistoryRepository extends JpaRepository<ScanHistory, Long> {

	List<ScanHistory> findTop10ByTeamIdOrderByHistorySeqDesc(Long teamId);

	List<ScanHistory> findAllByTeamIdOrderByHistorySeqDesc(Long teamId,Pageable page);

	List<ScanHistory> findByTeamIdAndHistorySeqLessThanOrderByHistorySeqDesc(Long teamId, Long historyseq, Pageable page);

	Boolean existsByTeamIdAndHistorySeqLessThan(Long teamId, Long historyseq);
}
