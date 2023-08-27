package com.initcloud.rocket23.team.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.common.feign.OAuthInfoFeignClient;
import com.initcloud.rocket23.security.config.SecurityProperties;
import com.initcloud.rocket23.security.provider.JwtProvider;
import com.initcloud.rocket23.team.dto.GithubRepositoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GithubAppService {
    private final String BASE_URL = "https://api.github.com";
    private final OAuthInfoFeignClient githubApiFeignClient;
    private final JwtProvider jwtProvider;

    private final SecurityProperties properties;

    public void redirectGithubApp(HttpServletResponse response) {
        try {
            response.sendRedirect("https://github.com/apps/initcloud-rocket");
        } catch (IOException e) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }
    }

    public String getAccessTokenFromGithubApp(String org) {
        String token = jwtProvider.createJwtToken();
        String installationId = getInstallationId(org);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("accept", "application/vnd.github.v3+json");

        Map<String, String> response = githubApiFeignClient.postGithubAppsAccessToken("Bearer " + token, installationId, body);

        return response.get("token");
    }

    public String getInstallationId(String org) {
        return "41124738";
    }

    public String getAccessToken(String owner) {
        return getAccessTokenFromGithubApp(owner);
    }

    public List<GithubRepositoryDto> getRepository(String owner) {
        //String accessToken = getAccessTokenFromGithubApp(owner);
        try {
            String accessToken = properties.getTmpToken();
            List<GithubRepositoryDto> apiResponse = githubApiFeignClient.getRepositoriesFromOrgs("Bearer " + accessToken, owner);

            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ResponseCode.INVALID_OWNER);
        }
    }
}
