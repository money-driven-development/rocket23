package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.team.entity.TeamProject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicySetPerProject extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLICY_SET_PER_PROJECT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POLICY_SET_ID")
    private PolicySet policySet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private TeamProject project;

    /**
     * Todo - 정책 셋, 프로젝트 코드 같이 여기서 관리
     */

    public PolicySetPerProject(PolicySet policySet, TeamProject project) {
        this.id = id;
        this.policySet = policySet;
        this.project = project;
    }
}
