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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, name = "BASE_POLICY_ID")
    private BasePolicy basePolicy;

    @Column(name = "POLICY_NAME", updatable = false)
    private String policyName;

    @Column(name = "BASE_POLICY_NAME", updatable = false)
    private String basePolicyName;

    @Column(name = "IS_BASE_POLICY", updatable = false)
    private boolean origin;

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
    private String severity;

    @Column(name = "SOLUTION", updatable = false)
    private String solution;

    @Column(name = "SOLUTION_CODE", updatable = false)
    private String solutionCode;

    @Column(name = "IN_SECURE_EXAMPLE", updatable = false)
    private String insecureExample;

    @Column(name = "SECURE_EXAMPLE", updatable = false)
    private String secureExample;

    @Column(name = "MEMO", updatable = false)
    private String memo;

    @OneToMany(mappedBy = "teamPolicy")
    private List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();

    @Builder(builderClassName = "policyBuilder", builderMethodName = "policyCreateBuilder")
    public TeamPolicy(Long id, Team team, BasePolicy basePolicy, String policyName, String basePolicyName,
                      boolean origin,
                      Provider policyProvider, Type policyType, Target policyTarget,
                      boolean isModifiable, String explanation, String explanationKr, String severity, String solution,
                      String solutionCode, String insecureExample, String secureExample, String memo) {
        this.id = id;
        this.team = team;
        this.basePolicy = basePolicy;
        this.policyName = policyName;
        this.basePolicyName = basePolicyName;
        this.origin = origin;
        this.policyProvider = policyProvider;
        this.policyType = policyType;
        this.policyTarget = policyTarget;
        this.isModifiable = isModifiable;
        this.explanation = explanation;
        this.explanationKr = explanationKr;
        this.severity = severity;
        this.solution = solution;
        this.solutionCode = solutionCode;
        this.insecureExample = insecureExample;
        this.secureExample = secureExample;
        this.memo = memo;
    }
}
