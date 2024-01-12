package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.PolicyPerPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.entity.PolicyPerPolicySet;
import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.policy.utils.PoliciesUtils;
import com.initcloud.rocket23.team.entity.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class TeamPolicySetService {

    private final TeamRepository teamRepository;
    private final TeamPolicyRepository teamPolicyRepository;
    private final TeamPolicySetRepository teamPolicySetRepository;
    private final PolicyPerPolicySetRepository policyPerPolicySetRepository;


    /* ========== 팀 정책 셋 ========== */

    /**
     * 팀 정책 셋 목록 조회
     */
    public List<String> getTeamPolicySetList(final String teamCode) {
        List<PolicySet> policySets = teamPolicySetRepository.findPolicySetByTeam_TeamCode(teamCode);

        return policySets.stream()
                .map(PolicySet::getName)
                .collect(Collectors.toList());
    }

    /**
     * 팀 정책 셋 세부 조회
     */
    public PolicySetDto getTeamPolicySetDetails(final String teamCode, final String policySetName) {
        PolicySet policySet = teamPolicySetRepository.findPolicySetByTeam_TeamCodeAndName(teamCode, policySetName)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        return policySet.toPolicySetDto();
    }


    /**
     * 팀 정책 셋 추가
     * Todo - 리팩토링 예정
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
        PolicySet policySet = teamPolicySetRepository.save(dto.toEntity(team));

        // 4. 정책 셋의 정책 목록에 정책 추가
        if (!dto.getPolicyState().isEmpty()) {
            List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();
            for (TeamPolicy policy : teamPolicies) {
                policiesPerPolicySet.add(new PolicyPerPolicySet(policySet, policy, true));
            }

            policyPerPolicySetRepository.saveAll(policiesPerPolicySet);
        }

        return dto.getPolicySetName();
    }

    /**
     * 팀 정책 셋 수정
     * 정책 셋 수정
     * 정책 추가/삭제/비활성화
     * Todo - 리팩토링 예정
     */
    public String modifyTeamPolicySet(final String teamCode, final String policySet, final PolicySetDto dto) {
        // 1. 수정 대상 기존 정책 셋을 찾고
        PolicySet originPolicySet = teamPolicySetRepository.findPolicySetByTeam_TeamCodeAndName(teamCode, policySet)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_POLICY_SET_IN_TEAM));

        // 3. 정책 셋의 항목 업데이트
        originPolicySet.updatePolicySet(dto);
        originPolicySet = teamPolicySetRepository.save(originPolicySet);

        // 2. 수정 대상 정책이 있다면
        if (!dto.getPolicyState().isEmpty()) {
            List<PolicyPerPolicySet> policiesPerPolicySet = policyPerPolicySetRepository.findPolicyPerPolicySetsByPolicySet(originPolicySet);
            policyPerPolicySetRepository.saveAll(policiesPerPolicySet);
        }

        return dto.getPolicySetName();
    }

    /**
     * 팀 정책 셋 삭제
     */
    public void deleteTeamPolicySet(final String teamCode, final String policySet) {
        teamPolicySetRepository.deleteTeamPolicySetByTeam_TeamCodeAndName(teamCode, policySet);
    }

}
