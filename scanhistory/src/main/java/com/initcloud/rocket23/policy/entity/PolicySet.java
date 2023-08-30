package com.initcloud.rocket23.policy.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.team.entity.Team;
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
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Column
    private String name;

    @Column
    private String description;

    /**
     * 정책 셋
     */
    @OneToMany(mappedBy = "policySet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();

    /**
     * 이 정책 셋을 사용하는 프로젝트
     */
    @OneToMany(mappedBy = "policySet")
    private List<PolicyPerPolicySet> projects = new ArrayList<>();

    public PolicySet(Team team, String name, String description) {
        this.team = team;
        this.name = name;
        this.description = description;
    }

    public void updatePolicySet(PolicySetDto dto) {
        if(dto.isNameModified())
            this.name = dto.getPolicySetName();

        if(dto.isDescriptionModified())
            this.description = dto.getDescription();
    }
}
