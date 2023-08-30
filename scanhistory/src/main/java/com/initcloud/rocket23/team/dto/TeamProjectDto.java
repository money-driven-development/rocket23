package com.initcloud.rocket23.team.dto;

import com.initcloud.rocket23.team.entity.TeamProject;
import com.initcloud.rocket23.team.entity.TeamProjectVersioning;
import com.initcloud.rocket23.team.enums.ProjectType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private String description;
        private LocalDateTime projectCreatedAt;

        @Builder
        public Summary(TeamProject teamProject) {
            this.projectType = teamProject.getProjectType();
            this.projectCode = teamProject.getProjectCode();
            this.projectName = teamProject.getProjectName();
            this.projectCreatedAt = teamProject.getCreatedAt();
            this.description = "DESCRIPTION_DEMO. DESCRIPTION_DEMO. DESCRIPTION_DEMO. DESCRIPTION_DEMO.";
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Details {
        private ProjectType projectType;
        private String projectCode;
        private String projectName;
        private String projectUrl;
        private String description;
        private LocalDateTime recentScanDateTime;
        private LocalDateTime projectCreatedAt;
        private LocalDateTime projectModifiedAt;
        private List<TeamProjectDto.Version> versionHistory = new ArrayList<>();

        @Builder
        public Details(ProjectType projectType, String projectCode, String projectName, String projectUrl, LocalDateTime recentScanDateTime, LocalDateTime projectCreatedAt, LocalDateTime projectModifiedAt, List<TeamProjectDto.Version> versionHistory) {
            this.projectType = projectType;
            this.projectCode = projectCode;
            this.projectName = projectName;
            this.projectUrl = projectUrl;
            this.recentScanDateTime = recentScanDateTime;
            this.projectCreatedAt = projectCreatedAt;
            this.projectModifiedAt = projectModifiedAt;
            this.versionHistory = versionHistory;
            this.description = "DESCRIPTION_DEMO. DESCRIPTION_DEMO. DESCRIPTION_DEMO. DESCRIPTION_DEMO.";
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
