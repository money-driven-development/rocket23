package com.initcloud.rocket23.team.dto;

import com.initcloud.rocket23.common.utils.UniqueUtils;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import com.initcloud.rocket23.team.enums.ProjectType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProjectCreateDto {
    @NotNull
    private String projectName;

    @NotNull
    private ProjectType projectType;

    private String projectUrl;

    public TeamProjectCreateDto(final String projectName, final ProjectType projectType, final String projectUrl) {
        this.projectName = projectName;
        this.projectType = projectType;
        this.projectUrl = projectUrl;
    }

    public TeamProject toCreateEntity(Team team) {
        return TeamProject.teamProjectCreateBuilder()
                .team(team)
                .projectCode(UniqueUtils.getUUID())
                .projectType(this.projectType)
                .projectUrl(this.projectUrl)
                .projectName(this.projectName)
                .build();
    }
}