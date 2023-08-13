package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.BasePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasePolicyRepository extends JpaRepository<BasePolicy, Long> {
}
