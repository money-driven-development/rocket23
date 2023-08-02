package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.checklist.entity.ScanHistoryDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanHistoryDetailRepository extends JpaRepository<ScanHistoryDetailEntity, Long> {
}
