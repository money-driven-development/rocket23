package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.team.entity.Team;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseToTeamPolicyService {

    private final TeamRepository teamRepository;
    private final TeamPolicyRepository teamPolicyRepository;
    private final BasePolicyRepository basePolicyRepository;

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


}
