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
    @JoinColumn(name = "POLICY_ID")
    private TeamPolicy teamPolicy;

    public PolicyPerPolicySet(PolicySet policySet, TeamPolicy teamPolicy) {
        this.policySet = policySet;
        this.teamPolicy = teamPolicy;
    }
}
