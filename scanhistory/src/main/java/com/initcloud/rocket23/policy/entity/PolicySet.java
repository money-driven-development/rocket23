package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicySet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLICY_SET_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Team team;

    @Column
    private String name;

    @Column
    private String description;

    /**
     * 정책 셋
     */
    @OneToMany(mappedBy = "policySet")
    private List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();

    /**
     * 이 정책 셋을 사용하는 프로젝트
     */
    @ManyToMany(mappedBy = "policySets")
    @JoinTable(name = "POLICY_SET_PER_PROJECT")
    private List<TeamProject> projects = new ArrayList<>();

    public PolicySet(Team team, String name, String description) {
        this.team = team;
        this.name = name;
        this.description = description;
    }
}
