package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.infra.repository.PolicyPerPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import com.initcloud.rocket23.policy.entity.PolicyPerPolicySet;
import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.team.entity.Team;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasePolicySetService {

    private final TeamRepository teamRepository;
    private final TeamPolicyRepository teamPolicyRepository;
    private final BasePolicyRepository basePolicyRepository;
    private final TeamPolicySetRepository teamPolicySetRepository;
    private final PolicyPerPolicySetRepository policyPerPolicySetRepository;

    public void basePolicyAllToTeamPolicy(String teamCode){
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        List<TeamPolicy> teamPolicies = teamPolicyRepository.findByTeam_Id(team.getId());
        if (teamPolicies.isEmpty()) {
            List<BasePolicy> basePolicies = basePolicyRepository.findAll();
            basePolicies.forEach(basePolicy -> basePolicyToTeamPolicy(team, basePolicy));
        }


    }

    public void basePolicyToTeamPolicy(Team team, BasePolicy basePolicy){

        TeamPolicy teamPolicy = TeamPolicy.policyCreateBuilder()
                .team(team)
                .policyName(basePolicy.getDefaultPolicyNameIC())
                .basePolicyName(basePolicy.getDefaultPolicyName())
                .origin(true)
                .policyContent(basePolicy.getPolicyContent())
                .policyContentKr(basePolicy.getPolicyContentKr())
                .policyProvider(basePolicy.getPolicyProvider())
                .policyType(basePolicy.getPolicyType())
                .policyTarget(basePolicy.getPolicyTarget())
                .isModifiable(basePolicy.isModifiable())
                .explanation(basePolicy.getExplanation())
                .explanationKr(basePolicy.getExplanationKr())
                .severity(basePolicy.getSeverity())
                .solution(basePolicy.getSolution())
                .solutionCode(basePolicy.getSolutionCode())
                .insecureCode(basePolicy.getInsecureCode())
                .secureCode(basePolicy.getSecureCode())
                .build();

        teamPolicyRepository.save(teamPolicy);
    }

    /**
     * Base Policy 정책 셋 팀 추가
     */
    @Transactional
    public String createBasePolicySet(final String teamCode) {

        // 1. 대상 팀을 찾고
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));


        // 2. 팀 정책 셋에 적용할 기본 정책들을 모아서
        List<TeamPolicy> baseTeamPolicies = teamPolicyRepository.findTeamPoliciesByTeam_Id(team.getId());

        PolicySet basePolicySet = PolicySet.builder()
                .team(team)
                .description("기본 제공되는 Base Policy Set 입니다.")
                .name("Base Policy Set")
                .build();

        // 3. 정책 셋을 만들고
        PolicySet policySet = teamPolicySetRepository.save(basePolicySet);

        // 4. 정책 셋의 정책 목록에 정책 추가
        List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();
        for (TeamPolicy policy : baseTeamPolicies) {
            policiesPerPolicySet.add(new PolicyPerPolicySet(policySet, policy, true));
        }

        policyPerPolicySetRepository.saveAll(policiesPerPolicySet);

        return null;
    }


}
