package com.initcloud.rocket23.policy.dto;

import com.initcloud.rocket23.common.enums.Policy;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.team.entity.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Summary {
        private String policyName;
        private Policy.Provider policyProvider;

        public Summary(final TeamPolicy policy) {
            this.policyName = policy.getPolicyName();
            this.policyProvider = policy.getPolicyProvider();
        }

        public static Page<Summary> toPageDto(final Page<TeamPolicy> policies) {
            return policies.map(policy -> new Summary(policy));
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Details {
        private String policyName;
        private Policy.Provider policyProvider;
        private Policy.Type policyType;
        private Policy.Target policyTarget;
        private boolean isModifiable;
        private String insecureExample;
        private String secureExample;
        private String code;

        @Builder
        public Details(final TeamPolicy policy) {
            this.policyName = policy.getPolicyName();
            this.policyProvider = policy.getPolicyProvider();
            this.policyType = policy.getPolicyType();
            this.policyTarget = policy.getPolicyTarget();
            this.isModifiable = policy.isModifiable();
            this.insecureExample = policy.getInsecureExample();
            this.secureExample = policy.getSecureExample();
            this.code = policy.getCode();
        }

        public Details toDetailsDto(final TeamPolicy policy) {
            return new Details(policy);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Create {

        @NotNull
        private String basePolicyName;

        @NotNull
        private String policyName;
        private String policyMemo;

        @NotNull
        private String policyDetails;

        public Create(String basePolicyName, String policyName, String policyDescription, String policyDetails) {
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
}
