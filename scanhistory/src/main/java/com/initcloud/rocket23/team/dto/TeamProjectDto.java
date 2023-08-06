package com.initcloud.rocket23.team.dto;

import com.initcloud.rocket23.common.utils.UniqueUtils;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import com.initcloud.rocket23.team.enums.ProjectType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamProjectDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Summary {
        private ProjectType projectType;
        private String projectCode;
        private String projectName;

        @Builder
        public Summary(TeamProject teamProject) {
            this.projectType = teamProject.getProjectType();
            this.projectCode = teamProject.getProjectCode();
            this.projectName = teamProject.getProjectName();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Details {
        private ProjectType projectType;
        private String projectCode;
        private String projectName;
        private String parentCode;
        private String sha1;
        private String sha2;
        private LocalDateTime recentScanDateTime;
        private List<Object> versionHistory = new ArrayList<>();
        private List<Object> scanHistory = new ArrayList<>();

        @Builder
        public Details(ProjectType projectType, String projectCode, String projectName, String parentCode, String sha1, String sha2, LocalDateTime recentScanDateTime, List versionHistory, List scanHistory) {
            this.projectType = projectType;
            this.projectCode = projectCode;
            this.projectName = projectName;
            this.parentCode = parentCode;
            this.sha1 = sha1;
            this.sha2 = sha2;
            this.recentScanDateTime = recentScanDateTime;
            this.versionHistory = versionHistory;
            this.scanHistory = scanHistory;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class Create {
        @NotNull
        private String projectName;

        @NotNull
        private ProjectType projectType;

        private String projectUrl;

        public Create(final String projectName, final ProjectType projectType, final String projectUrl) {
            this.projectName = projectName;
            this.projectType = projectType;
            this.projectUrl = projectUrl;
        }

        public TeamProject toCreateEntity(Team team) {
            return TeamProject.teamProjectCreateBuilder().team(team).projectCode(UniqueUtils.getUUID()).projectType(this.projectType).projectUrl(this.projectUrl).projectName(this.projectName).build();
        }
    }
}
