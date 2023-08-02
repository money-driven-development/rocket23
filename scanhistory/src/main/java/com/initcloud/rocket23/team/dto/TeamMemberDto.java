package com.initcloud.rocket23.team.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMemberDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Summary {

        @Email
        @NotNull
        private String email;
        private String profileImageUrl;
        private LocalDate joinDate;

        @Builder
        public Summary(String email, String profileImageUrl, LocalDate joinDate) {
            this.email = email;
            this.profileImageUrl = profileImageUrl;
            this.joinDate = joinDate;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Details {

        @Email
        @NotNull
        private String email;
        private String profileImageUrl;
        private LocalDate joinDate;
        private LocalDateTime lastLogin;
        private String contact;

        @NotNull
        private Boolean isAdmin;
        private String role;

        @Builder
        public Details(String email, String profileImageUrl, LocalDate joinDate, LocalDateTime lastLogin, String contact, Boolean isAdmin, String role) {
            this.email = email;
            this.profileImageUrl = profileImageUrl;
            this.joinDate = joinDate;
            this.lastLogin = lastLogin;
            this.contact = contact;
            this.isAdmin = isAdmin;
            this.role = role;
        }
    }
}
