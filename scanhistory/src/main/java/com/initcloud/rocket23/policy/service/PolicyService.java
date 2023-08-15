package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.PolicyPerPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.policy.utils.PoliciesUtils;
import com.initcloud.rocket23.team.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyService {

    // Todo - 요거 나중에 Join 쿼리로 묶을 예정
    private final TeamRepository teamRepository;
    private final TeamPolicyRepository teamPolicyRepository;
    private final TeamPolicySetRepository teamPolicySetRepository;
    private final PolicyPerPolicySetRepository policyPerPolicySetRepository;

    /**
     * 팀 정책 목록 조회 (페이징)
     */
    public Page<PolicyDto.Summary> getTeamPolicyList(final Pageable pageable, final String teamCode) {
        Page<TeamPolicy> teamProjectPolicies = teamPolicyRepository.findTeamPoliciesByTeam_TeamCode(pageable, teamCode);

        return PolicyDto.Summary.toPageDto(teamProjectPolicies);
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
    public String createTeamPolicy(final String teamCode, final PolicyDto.Create dto) {
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
    public String modifyTeamPolicy(final String teamCode, final PolicyDto.Create dto) {
        TeamPolicy policy = teamPolicyRepository.findTeamPolicyByTeam_TeamCodeAndBasePolicyName(teamCode, dto.getBasePolicyName())
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
        teamPolicyRepository.deleteTeamProjectTeamPolicyByBaseFalseAndTeam_TeamCodeAndPolicyName(teamCode, policyName);

        return policyName;
    }

    /**
     * 팀 정책 셋 추가
     */
    @Transactional
    public String createTeamPolicySet(final String teamCode, final PolicySetDto dto) {

        // 1. 대상 팀을 찾고
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        // 2. 팀 정책 셋에 적용할 정책들을 모아서
        List<String> policies = PoliciesUtils.toPolicyNameList(dto.getPolicyState());
        List<TeamPolicy> teamPolicies = teamPolicyRepository.findTeamPoliciesByTeamAndPolicyNameIn(team, policies);

        // 3. 정책 셋을 만들고
        PolicySet policySet = dto.toEntity(team);
        teamPolicySetRepository.save(policySet);

        // 4. 정책 셋의 정책 목록에 정책 추가
        policyPerPolicySetRepository.saveAll(null);

        return dto.getPolicySetName();
    }

    /**
     * 팀 정책 셋 수정
     * 정책 셋 수정
     * 정책 추가/삭제/비활성화
     */
    public String modifyTeamPolicySet(final String teamCode, final String policySet, final PolicySetDto dto) {
        PolicySet originPolicySet = teamPolicySetRepository.findPolicySetByTeam_TeamCodeAndName(teamCode, policySet)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_POLICY_SET_IN_TEAM));


        return null;
    }

    /**
     * 팀 정책 셋 삭제
     */
    public boolean deleteTeamPolicySet(final String teamCode, final String policySet) {

        return false;
    }
}
