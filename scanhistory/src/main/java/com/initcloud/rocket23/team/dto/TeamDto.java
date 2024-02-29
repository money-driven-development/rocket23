package com.initcloud.rocket23.team.dto;

import com.initcloud.rocket23.user.dto.UserDto;
import com.initcloud.rocket23.user.dto.UserDto.UserInfo;
import java.util.List;
import lombok.AccessLevel;
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
        private List<UserInfo> user;

        public TeamInfo(String teamName, String description, String teamCode, List<UserInfo> user) {
            this.teamName = teamName;
            this.description = description;
            this.teamCode = teamCode;
            this.user = user;
        }
    }

}
