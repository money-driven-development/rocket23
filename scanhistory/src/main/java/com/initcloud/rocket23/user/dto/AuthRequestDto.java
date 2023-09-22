package com.initcloud.rocket23.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthRequestDto {
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirect;

    public AuthRequestDto(String clientId, String clientSecret, String code, String redirect) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.redirect = redirect;
    }
}
