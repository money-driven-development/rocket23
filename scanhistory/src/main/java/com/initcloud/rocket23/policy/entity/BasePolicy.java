package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.enums.Policy;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
}
