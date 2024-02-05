package com.initcloud.rocket23.team.entity;

import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.utils.UniqueUtils;
import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.team.dto.TeamDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    @Column
    private String teamCode;

    @Column
    private String name;

    @Column
    private String logoUri;

    @Column
    private String description;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private List<ScanHistory> scanHistories = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    private List<PolicySet> policySets = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TeamProject> teamProjects = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TeamWithUsers> teamWithUsers = new ArrayList<>();

    public Team(Long id, String teamCode, String name, String logoUri, String description) {
        this.id = id;
        this.teamCode = teamCode;
        this.name = name;
        this.logoUri = logoUri;
        this.description = description;
    }

    public Team(final TeamDto.Team team) {
        this.teamCode = UniqueUtils.getUUID();
        this.name = team.getTeamName();
        this.logoUri = team.getLogoUri();
        this.description = team.getDescription();
    }
}
