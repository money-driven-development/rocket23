package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.checklist.entity.scanHistory.CodeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeBlockRepository extends JpaRepository<CodeBlock, Long> {
}
