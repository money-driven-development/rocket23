package com.initcloud.rocket23.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class AuthRequestDto {

    @Getter
    public static class github{
        @JsonProperty("client_id")
        private String clientId;

        @JsonProperty("client_secret")
        private String clientSecret;
        private String code;
        private String redirect;


        public github(String clientId, String clientSecret, String code, String redirect) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.code = code;
            this.redirect = redirect;
        }

    }

    @Getter
    public static class login{

        private String username;

        private String password;

        public login(String username, String password){
            this.username = username;
            this.password = password;
        }

        public login(){
            super();
        }
    }

}
