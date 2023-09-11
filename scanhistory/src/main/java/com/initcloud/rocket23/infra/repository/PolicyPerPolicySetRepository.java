package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.PolicyPerPolicySet;
import com.initcloud.rocket23.policy.entity.PolicySet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyPerPolicySetRepository extends JpaRepository<PolicyPerPolicySet, Long> {

    List<PolicyPerPolicySet> findPolicyPerPolicySetsByPolicySet(PolicySet policySet);
}
