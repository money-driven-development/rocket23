package com.initcloud.dockerapi.redis.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectUploadMessage {
    private String uuid;
    private String team;
    private String originName;
    private String project;
    private String fileType;
    private String provider;
    private String projectType;

    public ProjectUploadMessage(String uuid, String team, String originName, String project, String fileType, String provider, String projectType) {
        this.uuid = uuid;
        this.team = team;
        this.originName = originName;
        this.project = project;
        this.fileType = fileType;
        this.provider = provider;
        this.projectType = projectType;
    }
}
