package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.enums.Policy;
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
    @Column(name = "PROJECT_POLICY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private BasePolicy basePolicy;

    @Column
    private String policyName;

    @Column(updatable = false)
    private String basePolicyName;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Policy.Provider policyProvider;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Policy.Type policyType;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Policy.Target policyTarget;

    @Column(updatable = false)
    private boolean isBasePolicy;

    @Column(updatable = false)
    private boolean isModifiable;

    @Column
    private String memo;

    @Column
    private String insecureExample;

    @Column
    private String secureExample;

    @Column
    private String code;

    @Column
    private String customDetails;

    @OneToMany(mappedBy = "teamPolicy")
    private List<TeamPolicyDetails> details = new ArrayList<>();

    @OneToMany(mappedBy = "teamPolicy")
    private List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();

    @Builder(builderClassName = "policyBuilder", builderMethodName = "policyCreateBuilder")
    public TeamPolicy(Team team, BasePolicy basePolicy, String policyName, String basePolicyName, Policy.Provider policyProvider, Policy.Type policyType, Policy.Target policyTarget, boolean isBasePolicy, boolean isModifiable, String memo, String insecureExample, String secureExample, String code, String customDetails) {
        this.team = team;
        this.basePolicy = basePolicy;
        this.policyName = policyName;
        this.basePolicyName = basePolicyName;
        this.policyProvider = policyProvider;
        this.policyType = policyType;
        this.policyTarget = policyTarget;
        this.isBasePolicy = isBasePolicy;
        this.isModifiable = isModifiable;
        this.memo = memo;
        this.insecureExample = insecureExample;
        this.secureExample = secureExample;
        this.code = code;
        this.customDetails = customDetails;
    }
}
