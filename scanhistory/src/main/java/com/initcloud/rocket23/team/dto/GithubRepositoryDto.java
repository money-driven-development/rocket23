package com.initcloud.rocket23.team.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubRepositoryDto {
    private int id;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("private")
    private boolean isPrivate;

    @JsonProperty("download_url")
    private String downloadUrl;

    private String message;

    public GithubRepositoryDto(int id, String fullName, boolean isPrivate, String downloadUrl, String message) {
        this.id = id;
        this.fullName = fullName;
        this.isPrivate = isPrivate;
        this.downloadUrl = downloadUrl;
        this.message = message;
    }
}
