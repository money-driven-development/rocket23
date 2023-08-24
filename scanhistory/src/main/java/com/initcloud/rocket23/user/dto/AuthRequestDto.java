package com.initcloud.rocket23.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthRequestDto {

    private String code;

    public AuthRequestDto(String code) {
        this.code = code;
    }
}
