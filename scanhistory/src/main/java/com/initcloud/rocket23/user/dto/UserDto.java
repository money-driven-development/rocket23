package com.initcloud.rocket23.user.dto;

import com.initcloud.rocket23.team.entity.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    @Getter
    public static class MyTeam {
        private String teamCode;
        private String teamName;

        public MyTeam(final Team team) {
            this.teamCode = team.getTeamCode();
            this.teamName = team.getName();
        }
    }

    @Getter
    public class Profile {
        private String email;

        public Profile(String email) {
            this.email = email;
        }
    }
}
