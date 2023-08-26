package com.initcloud.rocket23.team.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamDto {
    private String teamName;
    private String description;
    private String logoUri;

    public TeamDto(String teamName, String description, String logoUri) {
        this.teamName = teamName;
        this.description = description;
        this.logoUri = logoUri;
    }
}
