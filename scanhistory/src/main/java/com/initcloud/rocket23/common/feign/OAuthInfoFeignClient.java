package com.initcloud.rocket23.common.feign;

import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.team.dto.GithubRepositoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(name = "feignClient", contextId = "oauthInfoFeignClient", url = "https://api.github.com")
public interface OAuthInfoFeignClient {

    @GetMapping(value = "/user")
    OAuthDto.GithubUserDetail getGithubUserDetail(
            @RequestHeader("Authorization") String token
    );

    @PostMapping(value = "/app/installations/{installationId}/access_tokens")
    Map<String, String> postGithubAppsAccessToken(
            @RequestHeader("Authorization") String token,
            @PathVariable String installationId,
            @RequestBody MultiValueMap<String, String> body
    );

    @GetMapping(value = "/orgs/{owner}/repos")
    List<GithubRepositoryDto> getRepositoriesFromOrgs(
            @RequestHeader("Authorization") String token,
            @PathVariable String owner
    );

    @GetMapping(value = "/users/{owner}/repos")
    List<GithubRepositoryDto> getRepositoriesFromUsers(
            @RequestHeader("Authorization") String token,
            @PathVariable String owner
    );
}
