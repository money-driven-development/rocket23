package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamProjectRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PolicyService {

    // Todo - 요거 나중에 Join 쿼리로 묶을 예정
    private final TeamRepository teamRepository;
    private final TeamProjectRepository teamProjectRepository;
    private final TeamPolicyRepository teamPolicyRepository;

    /**
     * 팀 정책 목록 조회 (페이징)
     */
    public Page<PolicyDto.Summary> getTeamPolicyList(final Pageable pageable, final String teamCode) {
        Page<TeamPolicy> teamProjectPolicies = teamPolicyRepository.findTeamProjectPoliciesByTeam_TeamCode(pageable, teamCode);

        return PolicyDto.Summary.toPageDto(teamProjectPolicies);
    }

    /**
     * 팀 정책 세부 조회
     */
    public PolicyDto.Details getTeamPolicyDetails(final String teamCode, final String policyName) {
        TeamPolicy policy = teamPolicyRepository.findTeamProjectPolicyByTeam_TeamCodeAndPolicyName(teamCode, policyName)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));

        return new PolicyDto.Details(policy);
    }

    /**
     * 팀 정책 추가
     */
    @Transactional
    public String createTeamPolicy(final String teamCode, final PolicyDto.Create dto) {
        TeamPolicy policy = teamPolicyRepository.findTeamProjectPolicyByTeam_TeamCodeAndBasePolicyName(teamCode, dto.getBasePolicyName())
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
    public String modifyTeamPolicy(final String teamCode, final PolicyDto.Create dto) {
        TeamPolicy policy = teamPolicyRepository.findTeamProjectPolicyByTeam_TeamCodeAndBasePolicyName(teamCode, dto.getBasePolicyName())
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
        TeamPolicy policy = teamPolicyRepository.findTeamProjectPolicyByTeam_TeamCodeAndBasePolicyName(teamCode, policyName)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));

        return policyName;
    }

    /**
     * 팀 정책 셋 추가
     */
    public String createTeamPolicySet(final String teamCode, final PolicySetDto.Create dto) {

        return null;
    }

    /**
     * 팀 정책 셋 수정
     * 정책 셋 수정
     * 정책 추가/삭제/비활성화
     */
    public String modifyTeamPolicySet(final String teamCode, final String policySet, final PolicySetDto.Modify dto) {

        return null;
    }

    public boolean deleteTeamPolicySet(final String teamCode, final String policySet) {

        return false;
    }
}
