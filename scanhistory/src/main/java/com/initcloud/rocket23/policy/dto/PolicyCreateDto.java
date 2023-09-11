package com.initcloud.rocket23.policy.dto;

import com.initcloud.rocket23.common.enums.Policy;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.team.entity.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyCreateDto {

    @NotNull
    private String basePolicyName;

    @NotNull
    private String policyName;
    private String policyMemo;

    @NotNull
    private String policyDetails;

    public PolicyCreateDto(String basePolicyName, String policyName, String policyDescription, String policyDetails) {
        this.basePolicyName = basePolicyName;
        this.policyName = policyName;
        this.policyMemo = policyDescription;
        this.policyDetails = policyDetails;
    }

    public TeamPolicy toTeamPolicyEntity(final TeamPolicy teamPolicy) {
        BasePolicy basePolicy = teamPolicy.getBasePolicy();
        Team team = teamPolicy.getTeam();

        return TeamPolicy.policyCreateBuilder()
                .basePolicy(basePolicy)
                .team(team)

                .basePolicyName(this.basePolicyName)
                .policyName(this.policyName)
                .memo(this.policyMemo)
                .customDetails(this.policyDetails)

                .secureExample(basePolicy.getSecureExample())
                .insecureExample(basePolicy.getInsecureExample())

                .isOrigin(false)

                // Todo
                .policyProvider(Policy.Provider.NONE)
                .policyType(Policy.Type.NONE)
                .policyTarget(Policy.Target.NONE)

                .build();
    }
}