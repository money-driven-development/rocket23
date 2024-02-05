package com.initcloud.rocket23.team.dto;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Team {

        private String teamName;
        private String description;
        private String logoUri;

        public Team(String teamName, String description, String logoUri) {
            this.teamName = teamName;
            this.description = description;
            this.logoUri = logoUri;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TeamInfo {

        private String teamName;
        private String description;
        private String teamCode;

        public TeamInfo(String teamName, String description, String teamCode) {
            this.teamName = teamName;
            this.description = description;
            this.teamCode = teamCode;
        }
    }

}
