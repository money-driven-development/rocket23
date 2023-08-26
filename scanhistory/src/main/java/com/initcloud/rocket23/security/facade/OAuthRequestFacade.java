package com.initcloud.rocket23.security.facade;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.common.feign.OAuthFeignClient;
import com.initcloud.rocket23.common.feign.OAuthInfoFeignClient;
import com.initcloud.rocket23.security.config.SecurityProperties;
import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.provider.JwtProvider;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthRequestFacade {

    private final OAuthFeignClient oauthFeignClient;
    private final OAuthInfoFeignClient infoFeignClient;
    private final JwtProvider jwtProvider;
    private final SecurityProperties properties;

    private static final String GITHUB_AUTH_URL = "https://github.com/login/oauth/authorize?";

    /**
     * Request Github Access Token by auth code.
     *
     * @return access token in String
     */
    public String requestGithubOAuthToken(String code) {
        OAuthDto.GithubTokenRequest tokenRequest = new OAuthDto.GithubTokenRequest(properties, code);
        String resultString = oauthFeignClient.requestGithubAccessToken(tokenRequest);

        String[] arr = resultString.split("&");

        for (String s : arr) {
            if (s.startsWith("access_token"))
                return s;
        }

        throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
    }

    /**
     * Request Github User info by access token.
     *
     * @return user detail in OAuthDto.GithubUserDetail
     */
    public OAuthDto.GithubUserDetail requestGithubUserDetail(String accessToken) {
        return infoFeignClient.getGithubUserDetail("Bearer " + accessToken.split("=")[1]);
    }

    /**
     * You can get user access token
     *
     * @return user access token in Token
     */
    public Token createSocialUserToken(String username) {
        return jwtProvider.create(username, properties.getSecret());
    }

    public String getRedirectAuthUrl(String redirect) {
        return GITHUB_AUTH_URL + "client_id=" + properties.getGithubClientId() + "&redirect_uri=" + redirect;
    }
}
