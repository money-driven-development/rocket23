package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.BasePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePolicyRepository extends JpaRepository<BasePolicy, Long> {
    BasePolicy findByDefaultPolicyNameIC(String checkId);

    BasePolicy findByDefaultPolicyName(String policyName);
}
