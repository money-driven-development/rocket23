package com.initcloud.rocket23.team.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamInviteDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        @Email @NotNull
        private String email;

        @NotNull
        private String teamCode;

        public Request(String email, String teamCode) {
            this.email = email;
            this.teamCode = teamCode;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        @Email @NotNull
        private String email;

        @NotNull
        private String teamCode;

        @NotNull
        private Boolean join;

        public Response(String email, String teamCode, Boolean join) {
            this.email = email;
            this.teamCode = teamCode;
            this.join = join;
        }
    }

}
