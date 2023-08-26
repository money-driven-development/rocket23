package com.initcloud.rocket23.team.dto;

import com.initcloud.rocket23.common.utils.UniqueUtils;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import com.initcloud.rocket23.team.entity.TeamProjectVersioning;
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
    public static class Details {
        private ProjectType projectType;
        private String projectCode;
        private String projectName;
        private String projectUrl;
        private LocalDateTime recentScanDateTime;
        private List<TeamProjectDto.Version> versionHistory = new ArrayList<>();

        @Builder
        public Details(ProjectType projectType, String projectCode, String projectName, String projectUrl, LocalDateTime recentScanDateTime, List<TeamProjectDto.Version> versionHistory) {
            this.projectType = projectType;
            this.projectCode = projectCode;
            this.projectName = projectName;
            this.projectUrl = projectUrl;
            this.recentScanDateTime = recentScanDateTime;
            this.versionHistory = versionHistory;
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
            return TeamProject.teamProjectCreateBuilder()
                    .team(team)
                    .projectCode(UniqueUtils.getUUID())
                    .projectType(this.projectType)
                    .projectUrl(this.projectUrl)
                    .projectName(this.projectName)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Version {
        private String parentSha1;
        private String parentSha2;
        private String sha1;
        private String sha2;

        public Version(final TeamProjectVersioning version) {
            if (version.getParent() == null) {
                this.parentSha1 = null;
                this.parentSha2 = null;
            } else {
                this.parentSha1 = version.getParent().getVersionHashSHA1();
                this.parentSha2 = version.getParent().getVersionHashSHA2();
            }
            this.sha1 = version.getVersionHashSHA1();
            this.sha2 = version.getVersionHashSHA2();
        }
    }
}
