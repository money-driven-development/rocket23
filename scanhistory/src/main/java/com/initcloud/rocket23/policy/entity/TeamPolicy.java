package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.enums.Policy;
import com.initcloud.rocket23.common.enums.Policy.Provider;
import com.initcloud.rocket23.common.enums.Policy.Target;
import com.initcloud.rocket23.common.enums.Policy.Type;
import com.initcloud.rocket23.team.entity.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamPolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_POLICY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Column(name = "POLICY_NAME", updatable = false)
    private String policyName;

    @Column(name = "BASE_POLICY_NAME", updatable = false)
    private String basePolicyName;

    @Column(name = "IS_BASE_POLICY", updatable = false)
    private boolean origin;

    @Column(updatable = false, name = "POLICY_CONTENT")
    private String policyContent;

    @Column(updatable = false, name = "POLICY_CONTENT_KR")
    private String policyContentKr;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Policy.Provider policyProvider;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Policy.Type policyType;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Policy.Target policyTarget;

    @Column(name = "IS_MODIFIABLE", updatable = false)
    private boolean isModifiable;

    @Column(name = "EXPLANATION", updatable = false)
    private String explanation;

    @Column(name = "EXPLANATION_KR", updatable = false)
    private String explanationKr;

    @Column(name = "SEVERITY", updatable = false)
    @Enumerated(EnumType.STRING)
    private Policy.Severity severity;

    @Column(name = "SOLUTION", updatable = false)
    private String solution;

    @Column(updatable = false, name = "SOLUTION_CODE")
    private String solutionCode;

    @Column(updatable = false, name = "INSECURE_CODE")
    private String insecureCode;

    @Column(updatable = false, name = "SECURE_CODE")
    private String secureCode;

    @Column(name = "MEMO", updatable = false)
    private String memo;

    @OneToMany(mappedBy = "teamPolicy", cascade = CascadeType.ALL)
    private List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();

    @Builder(builderClassName = "policyBuilder", builderMethodName = "policyCreateBuilder")
    public TeamPolicy(Long id, Team team, String policyName, String basePolicyName,
                      boolean origin, String policyContent, String policyContentKr,
                      Provider policyProvider, Type policyType, Target policyTarget,
                      boolean isModifiable, String explanation, String explanationKr, Policy.Severity severity, String solution,
                      String solutionCode, String insecureCode, String secureCode, String memo) {
        this.id = id;
        this.team = team;
        this.policyName = policyName;
        this.basePolicyName = basePolicyName;
        this.origin = origin;
        this.policyContent = policyContent;
        this.policyContentKr = policyContentKr;
        this.policyProvider = policyProvider;
        this.policyType = policyType;
        this.policyTarget = policyTarget;
        this.isModifiable = isModifiable;
        this.severity = severity;
        this.explanation = explanation;
        this.explanationKr = explanationKr;
        this.solution = solution;
        this.solutionCode = solutionCode;
        this.insecureCode = insecureCode;
        this.secureCode = secureCode;
        this.memo = memo;
    }
}
