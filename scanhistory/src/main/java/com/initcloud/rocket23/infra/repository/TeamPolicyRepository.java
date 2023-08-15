package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.TeamPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamPolicyRepository extends JpaRepository<TeamPolicy, Long> {

    Page<TeamPolicy> findTeamProjectPoliciesByTeam_TeamCode(Pageable pageable, String teamCode);

    Optional<TeamPolicy> findTeamProjectPolicyByTeam_TeamCodeAndPolicyName(String teamCode, String policyName);

    Optional<TeamPolicy> findTeamProjectPolicyByTeam_TeamCodeAndBasePolicyName(String teamCode, String basePolicyName);
}
