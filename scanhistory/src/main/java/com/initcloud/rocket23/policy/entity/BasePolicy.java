package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.enums.Policy;
import com.initcloud.rocket23.common.enums.Policy.Provider;
import com.initcloud.rocket23.common.enums.Policy.Target;
import com.initcloud.rocket23.common.enums.Policy.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLICY_ID")
    private Long id;

    @Column(updatable = false, name = "DEFAULT_POLICY_NAME")
    private String defaultPolicyName;

    @Column(updatable = false, name = "DEFAULT_POLICY_NAME_IC")
    private String defaultPolicyNameIC;

    @Column(updatable = false, name = "POLICY_CONTENT")
    private String policyContent;

    @Column(updatable = false, name = "POLICY_CONTENT_KR")
    private String policyContentKr;

    @Column(updatable = false, name = "POLICY_PROVIDER")
    @Enumerated(EnumType.STRING)
    private Policy.Provider policyProvider;

    @Column(updatable = false, name = "POLICY_TYPE")
    @Enumerated(EnumType.STRING)
    private Policy.Type policyType;

    @Column(updatable = false, name = "POLICY_TARGET")
    @Enumerated(EnumType.STRING)
    private Policy.Target policyTarget;

    @Column(updatable = false, name = "IS_MODIFIABLE")
    private boolean isModifiable;

    @Column(updatable = false, name = "SEVERITY")
    @Enumerated(EnumType.STRING)
    private Policy.Severity severity;

    @Column(updatable = false, name = "EXPLANATION")
    private String explanation;

    @Column(updatable = false, name = "EXPLANATION_KOR")
    private String explantionKor;

    @Column(updatable = false, name = "SOLUTION")
    private String solution;

    @Column(updatable = false, name = "SOLUTION_CODE")
    private String solutionCode;

    @Column(updatable = false, name = "INSECURE_CODE")
    private String insecureCode;

    @Column(updatable = false, name = "SECURE_CODE")
    private String secureCode;

    public BasePolicy(Long id, String defaultPolicyName, String defaultPolicyNameIC, String policyContent,
                      String policyContentKr, Provider policyProvider, Type policyType,
                      Target policyTarget, boolean isModifiable, Policy.Severity severity,
                      String explanation, String explantionKor,
                      String solution, String solutionCode,
                      String insecureCode, String secureCode) {
        this.id = id;
        this.defaultPolicyName = defaultPolicyName;
        this.defaultPolicyNameIC = defaultPolicyNameIC;
        this.policyContent = policyContent;
        this.policyContentKr = policyContentKr;
        this.policyProvider = policyProvider;
        this.policyType = policyType;
        this.policyTarget = policyTarget;
        this.isModifiable = isModifiable;
        this.severity = severity;
        this.explanation = explanation;
        this.explantionKor = explantionKor;
        this.solution = solution;
        this.solutionCode = solutionCode;
        this.insecureCode = insecureCode;
        this.secureCode = secureCode;
    }
}
