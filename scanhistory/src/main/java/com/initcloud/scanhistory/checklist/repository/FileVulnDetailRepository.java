package com.initcloud.scanhistory.checklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.scanhistory.checklist.entity.FileVulnDetailEntity;

@Repository
public interface FileVulnDetailRepository extends JpaRepository<FileVulnDetailEntity, Long> {
}
