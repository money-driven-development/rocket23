package com.initcloud.rocket23.security.service;

import com.initcloud.rocket23.security.dto.Token;

public interface AuthService {

    Token getUserAccessToken(String code);
}
