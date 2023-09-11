package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.PolicySet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamPolicySetRepository extends JpaRepository<PolicySet, Long> {

    Optional<PolicySet> findPolicySetByTeam_TeamCodeAndName(String teamCode, String name);

    List<PolicySet> findPolicySetByTeam_TeamCodeAndNameIn(String teamCode, List<String> name);

    List<PolicySet> findPolicySetByTeam_TeamCode(String teamCode);

    void deleteTeamPolicySetByTeam_TeamCodeAndName(String teamCode, String name);
}
