package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.PolicyPerPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.dto.PolicyCreateDto;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.entity.PolicyPerPolicySet;
import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.policy.utils.PoliciesUtils;
import com.initcloud.rocket23.team.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamPolicyService {

    // Todo - 요거 나중에 Join 쿼리로 묶을 예정
    private final TeamRepository teamRepository;
    private final TeamPolicyRepository teamPolicyRepository;
    private final TeamPolicySetRepository teamPolicySetRepository;
    private final PolicyPerPolicySetRepository policyPerPolicySetRepository;

    /**
     * 팀 정책 목록 조회 (페이징)
     */
    public Page<PolicyDto.Summary> getPagedTeamPolicyList(final Pageable pageable, final String teamCode) {
        Page<TeamPolicy> teamProjectPolicies = teamPolicyRepository.findTeamPoliciesByTeam_TeamCode(pageable, teamCode);

        return PolicyDto.Summary.toPageDto(teamProjectPolicies);
    }

    /**
     * 팀 정책 목록 조회 (전체)
     */
    public List<PolicyDto.Summary> getTeamPolicyList(final String teamCode) {
        List<TeamPolicy> teamProjectPolicies = teamPolicyRepository.findTeamPoliciesByTeam_TeamCode(teamCode);

        return teamProjectPolicies.stream()
                .map(PolicyDto.Summary::new)
                .collect(Collectors.toList());
    }

    /**
     * 팀 정책 세부 조회
     */
    public PolicyDto.Details getTeamPolicyDetails(final String teamCode, final String policyName) {
        TeamPolicy policy = teamPolicyRepository.findTeamPolicyByTeam_TeamCodeAndPolicyName(teamCode, policyName)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));

        return new PolicyDto.Details(policy);
    }

    /**
     * 팀 정책 추가
     */
    @Transactional
    public String createTeamPolicy(final String teamCode, final PolicyCreateDto dto) {
        TeamPolicy policy = teamPolicyRepository.findTeamPolicyByTeam_TeamCodeAndBasePolicyName(teamCode, dto.getBasePolicyName())
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));

        teamPolicyRepository.save(
                dto.toTeamPolicyEntity(policy)
        );

        return dto.getPolicyName();
    }

    /**
     * 팀 정책 수정
     */
    @Transactional
    public String modifyTeamPolicy(final String teamCode, final String policyName, final PolicyCreateDto dto) {
        TeamPolicy policy = teamPolicyRepository.findTeamPolicyByTeam_TeamCodeAndOriginFalseAndIsModifiableTrueAndBasePolicyName(teamCode, policyName)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));

        teamPolicyRepository.save(
                dto.toTeamPolicyEntity(policy)
        );

        return dto.getPolicyName();
    }

    /**
     * 팀 정책 삭제
     */
    public String deleteTeamPolicy(final String teamCode, final String policyName) {
        teamPolicyRepository.deleteTeamPolicyByOriginFalseAndTeam_TeamCodeAndPolicyName(teamCode, policyName);

        return policyName;
    }
}