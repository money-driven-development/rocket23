package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.PolicyPerPolicySet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyPerPolicySetRepository extends JpaRepository<PolicyPerPolicySet, Long> {
}
