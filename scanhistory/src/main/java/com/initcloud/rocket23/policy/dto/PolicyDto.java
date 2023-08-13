package com.initcloud.rocket23.policy.dto;

import com.initcloud.rocket23.common.enums.Policy;
import com.initcloud.rocket23.policy.entity.TeamProjectPolicy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Summary {
        private String policyName;
        private Policy.Provider policyProvider;

        public Summary(final TeamProjectPolicy policy) {
            this.policyName = policy.getPolicyNameIC();
            this.policyProvider = policy.getPolicyProvider();
        }

        public static Page<Summary> toPageDto(final Page<TeamProjectPolicy> policies) {
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
        public Details(TeamProjectPolicy policy) {
            this.policyName = policy.getPolicyNameIC();
            this.policyProvider = policy.getPolicyProvider();
            this.policyType = policy.getPolicyType();
            this.policyTarget = policy.getPolicyTarget();
            this.isModifiable = policy.isModifiable();
            this.insecureExample = policy.getInsecureExample();
            this.secureExample = policy.getSecureExample();
            this.code = policy.getCode();
        }

        public Details toDetailsDto(final TeamProjectPolicy policy) {
            return new Details(policy);
        }
    }
}
