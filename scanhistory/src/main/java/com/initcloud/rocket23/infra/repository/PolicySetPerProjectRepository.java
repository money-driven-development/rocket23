package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.PolicySetPerProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicySetPerProjectRepository extends JpaRepository<PolicySetPerProject, Long> {
}
