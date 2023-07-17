package com.initcloud.rocket23.common.feign;

import com.initcloud.rocket23.security.dto.OAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "feignClient", contextId = "oauthFeignClient", url = "https://github.com")
public interface OAuthFeignClient {

    @PostMapping(value = "/login/oauth/access_token")
    String requestGithubAccessToken(@RequestBody OAuthDto.GithubTokenRequest tokenRequest);
}