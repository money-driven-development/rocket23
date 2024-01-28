package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.team.entity.Team;
import feign.Param;
import java.util.Collection;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface TeamPolicyRepository extends JpaRepository<TeamPolicy, Long> {

    Page<TeamPolicy> findTeamPoliciesByTeam_TeamCode(Pageable pageable, String teamCode);

    List<TeamPolicy> findTeamPoliciesByTeam_TeamCode(String teamCode);

    List<TeamPolicy> findTeamPoliciesByTeamAndPolicyNameIn(Team team, List<String> policyNames);

    Optional<TeamPolicy> findTeamPolicyByTeam_TeamCodeAndPolicyName(String teamCode, String policyName);

    Optional<TeamPolicy> findTeamPolicyByTeam_TeamCodeAndBasePolicyName(String teamCode, String basePolicyName);

    Optional<TeamPolicy> findTeamPolicyByTeam_TeamCodeAndOriginFalseAndIsModifiableTrueAndBasePolicyName(String teamCode, String basePolicyName);

    void deleteTeamPolicyByOriginFalseAndTeam_TeamCodeAndPolicyName(String teamCode, String policyName);

    List<TeamPolicy> findByTeam(Team team);

    @Query("SELECT tp FROM TeamPolicy tp WHERE tp.team.id = :teamId AND tp.origin = true")
    List<TeamPolicy> findTeamPoliciesByTeam_Id(@Param("teamId") Long teamId);

    TeamPolicy findByTeam_TeamCodeAndPolicyName(String teamCode, String policyName);
}
