package com.initcloud.rocket23.policy.dto;

import com.initcloud.rocket23.common.enums.Policy;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePolicyDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Details{

        private String defaultPolicyName;
        private String defaultPolicyNameIc;
        private String policyContent;
        private String policyContentKr;
        private Policy.Provider policyProvider;
        private Policy.Type policyType;
        private Policy.Target policyTarget;
        private boolean isModifiable;
        private Policy.Severity severity;
        private String explanation;
        private String explanationKor;
        private String solution;
        private String solutionCode;
        private String insecureCode;
        private String secureCode;

        public Details(final BasePolicy basePolicy) {
            this.defaultPolicyName = basePolicy.getDefaultPolicyName();
            this.defaultPolicyNameIc = basePolicy.getDefaultPolicyNameIC();
            this.policyContent = basePolicy.getPolicyContent();
            this.policyContentKr = basePolicy.getPolicyContentKr();
            this.policyProvider = basePolicy.getPolicyProvider();
            this.policyType = basePolicy.getPolicyType();
            this.policyTarget = basePolicy.getPolicyTarget();
            this.isModifiable = basePolicy.isModifiable();
            this.severity = basePolicy.getSeverity();
            this.explanation = basePolicy.getExplanation();
            this.explanationKor = basePolicy.getExplantionKor();
            this.solution = basePolicy.getSolution();
            this.solutionCode = basePolicy.getSolutionCode();
            this.insecureCode = basePolicy.getInsecureCode();
            this.secureCode = basePolicy.getSecureCode();
        }

        public static Page<Details> toPageDto(final Page<BasePolicy> basePolicies) {
            return basePolicies.map(Details::new);
        }
    }





}
