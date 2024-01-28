package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
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
    @JoinColumn(name = "POLICY_SET_ID")
    private PolicySet policySet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_POLICY_ID")
    private TeamPolicy teamPolicy;

    @Column
    private boolean state;

    @Builder
    public PolicyPerPolicySet(PolicySet policySet, TeamPolicy teamPolicy, boolean state) {
        this.policySet = policySet;
        this.teamPolicy = teamPolicy;
        this.state = state;
    }

    public void updateState(boolean state) {
        this.state = state;
    }
}
