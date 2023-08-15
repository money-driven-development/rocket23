package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicyPerPolicySet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLICY_PER_POLICY_SET_ID")
    private Long id;

    @ManyToOne
    @JoinColumn
    private PolicySet policySet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TeamPolicy policy;

    @Column
    private boolean isActivated;

    public PolicyPerPolicySet(Long id, PolicySet policySet, TeamPolicy policy, boolean isActivated) {
        this.id = id;
        this.policySet = policySet;
        this.policy = policy;
        this.isActivated = isActivated;
    }
}
