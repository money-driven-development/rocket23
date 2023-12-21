package com.initcloud.rocket23.policy.dto;

import com.initcloud.rocket23.common.enums.Policy;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
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

        public Summary(final TeamPolicy policy) {
            this.policyName = policy.getPolicyName();
            this.policyProvider = policy.getPolicyProvider();
        }

        public static Page<Summary> toPageDto(final Page<TeamPolicy> policies) {
            return policies.map(Summary::new);
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
        }

        public Details toDetailsDto(final TeamPolicy policy) {
            return new Details(policy);
        }
    }
}
