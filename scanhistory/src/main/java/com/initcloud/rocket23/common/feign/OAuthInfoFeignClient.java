package com.initcloud.rocket23.common.feign;

import com.initcloud.rocket23.security.dto.OAuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "feignClient", contextId = "oauthInfoFeignClient", url = "https://api.github.com")
public interface OAuthInfoFeignClient {

    @GetMapping(value = "/user")
    OAuthDto.GithubUserDetail requestGithubUserDetail(@RequestHeader("Authorization") String token);
}
