package com.initcloud.rocket23.team.dto;

import com.initcloud.rocket23.common.utils.UniqueUtils;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import com.initcloud.rocket23.team.enums.ProjectType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProjectCreateDto {
    @NotNull
    private String projectName;

    @NotNull
    private ProjectType projectType;

    private String projectUrl;

    private String description;

    private List<String> policySetNames = new ArrayList<>();

    public TeamProjectCreateDto(final String projectName, final ProjectType projectType, final String projectUrl, final String description) {
        this.projectName = projectName;
        this.projectType = projectType;
        this.projectUrl = projectUrl;
        this.description = description;
    }

    public TeamProject toCreateEntity(Team team) {
        return TeamProject.teamProjectCreateBuilder()
                .team(team)
                .projectCode(UniqueUtils.getUUID())
                .projectType(this.projectType)
                .projectUrl(this.projectUrl)
                .projectName(this.projectName)
                .description(this.description)
                .build();
    }
}