package com.initcloud.rocket23.team.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.team.dto.TeamProjectDto;
import com.initcloud.rocket23.team.enums.ProjectType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
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
}
