package com.initcloud.rocket23.user.dto;

import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.enums.UserState;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
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
    public static class Profile {
        private String email;

        public Profile(User user) {
            this.email = user.getEmail();
        }
    }


    @Getter
    @Builder
    public static class UserInfo {

        private UserState userState;
        private String username;
        private String password;
        private String profileImageUrl;
        private String contact;
        private String email;

        public User toDto(UserState userState, String username, String password, String email, String contact, String profileImageUrl) {
            return User.builder()
                    .userState(userState)
                    .username(username)
                    .password(password)
                    .email(email)
                    .contact(contact)
                    .profileImageUrl(profileImageUrl)
                    .build();
        }

        public static UserInfo of(User user){
            return UserInfo.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .contact(user.getContact())
                    .profileImageUrl(user.getProfileImageUrl())
                    .build();
        }

    }

    @Getter
    public static class modifyUser{
        private String profileImageUrl;
        private String contact;
        private String email;
    }
}
