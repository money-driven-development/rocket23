package com.initcloud.rocket23.common.feign;

import com.initcloud.rocket23.security.dto.OAuthDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "feignClient", contextId = "oauthFeignClient", url = "https://github.com")
public interface OAuthFeignClient {

    @PostMapping(value = "/login/oauth/access_token")
    @Headers("Accept: application/vnd.github+json")
    OAuthDto.GithubTokenResponse requestGithubAccessToken(
            @RequestParam(value = "client_id", required = true) String clientId,
            @RequestParam(value = "client_secret", required = true) String clientSecret,
            @RequestParam(value = "code", required = true) String code,
            @RequestParam(value = "redirect_uri", required = false) String redirect);
}