package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.checklist.entity.FileVulnDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileVulnDetailRepository extends JpaRepository<FileVulnDetailEntity, Long> {
}
