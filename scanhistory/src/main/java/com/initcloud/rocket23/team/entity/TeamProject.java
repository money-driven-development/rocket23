package com.initcloud.rocket23.team.entity;

import com.initcloud.rocket23.checklist.entity.ScanHistory;
import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.entity.PolicySetPerProject;
import com.initcloud.rocket23.team.dto.TeamProjectDto;
import com.initcloud.rocket23.team.enums.ProjectType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Column
    private String projectCode;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ProjectType projectType;

    @Column
    private String projectUrl;

    @Column
    private String projectName;

    @OneToMany(mappedBy = "project")
    private List<ScanHistory> scanHistories = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<TeamProjectVersioning> versions = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<PolicySetPerProject> policySets = new ArrayList<>();


    @Builder(builderClassName = "teamProjectBuilder", builderMethodName = "teamProjectCreateBuilder")
    public TeamProject(Team team, String projectCode, ProjectType projectType, String projectUrl, String projectName) {
        this.team = team;
        this.projectCode = projectCode;
        this.projectType = projectType;
        this.projectUrl = projectUrl;
        this.projectName = projectName;
    }

    public static Page<TeamProjectDto.Summary> toPageDto(Page<TeamProject> teamProjects) {
        return teamProjects.map(project -> new TeamProjectDto.Summary(project));
    }

    public static List<TeamProjectDto.Summary> toDto(List<TeamProject> teamProjects) {
        return teamProjects.stream().map(TeamProjectDto.Summary::new).collect(Collectors.toList());
    }

    public TeamProjectDto.Details toDetailsDto() {
        return TeamProjectDto.Details.builder()
                .projectName(this.projectName)
                .projectType(this.projectType)
                .projectCode(this.projectCode)
                .projectUrl(this.projectUrl)
                .versionHistory(
                        this.versions.stream()
                                .map(TeamProjectDto.Version::new)
                                .collect(Collectors.toList())
                )
                .policySets(
                        this.policySets.stream()
                                .map(policySetPerProject -> new PolicySetDto(policySetPerProject.getPolicySet()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
