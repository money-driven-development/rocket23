package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.enums.Policy;
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
    @Column(name = "BASE_POLICY_ID")
    private Long id;

    @Column(updatable = false)
    private String defaultPolicyName;

    @Column(updatable = false)
    private String defaultPolicyNameIC;

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
    private boolean isModifiable;

    @Column(updatable = false)
    private String insecureExample;

    @Column(updatable = false)
    private String secureExample;

    @Column(updatable = false)
    private String code;

    @Column(updatable = false)
    private String customDefault;

    @OneToMany(mappedBy = "basePolicy")
    private List<BasePolicyDetails> details = new ArrayList<>();

    public BasePolicy(Long id, String defaultPolicyName, String defaultPolicyNameIC, Policy.Provider policyProvider, Policy.Type policyType, Policy.Target policyTarget, boolean isModifiable, String insecureExample, String secureExample, String code, String customDefault) {
        this.id = id;
        this.defaultPolicyName = defaultPolicyName;
        this.defaultPolicyNameIC = defaultPolicyNameIC;
        this.policyProvider = policyProvider;
        this.policyType = policyType;
        this.policyTarget = policyTarget;
        this.isModifiable = isModifiable;
        this.insecureExample = insecureExample;
        this.secureExample = secureExample;
        this.code = code;
        this.customDefault = customDefault;
    }
}
