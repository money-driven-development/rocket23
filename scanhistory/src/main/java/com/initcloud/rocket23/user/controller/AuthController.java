package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.security.service.OAuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@ApiOperation("Auth (Login/Register)")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService authService;

    public ResponseDto<Object> githubUserRegister() {
        //Todo
        return new ResponseDto<>(null);
    }

    public ResponseDto<Object> githubUserJoin() {
        //Todo
        return new ResponseDto<>(null);
    }

    @ApiOperation(value = "Redirect to Github Login page.", notes = "Redirect to Github Login page to get an auth code.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "redirect", paramType = "query", value = "Redirect Url for FE", required = true, dataTypeClass = String.class)})
    @GetMapping("/github")
    public void githubAuthRedirect(HttpServletResponse response, @RequestParam("redirect") String redirect) {
        authService.redirectGithub(response, redirect);
    }

    @ApiOperation(value = "Redirect to Github Login page.", notes = "Redirect to Github Login page to get an auth code.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", paramType = "quary", value = "client_id from github OAuth", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "client_secret", paramType = "quary", value = "client_secret Code from github OAuth", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "code", paramType = "quary", value = "Authorization Code from github", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "redirect", paramType = "quary", value = "Redirect Url for FE", required = true, dataTypeClass = String.class)})
    @PostMapping("/callback")
    public ResponseDto<OAuthDto.GithubTokenResponse> githubAuth(
            @RequestParam(value = "client_id", required = true) String clientId,
            @RequestParam(value = "client_secret", required = true) String clientSecret,
            @RequestParam(value = "code", required = true) String code,
            @RequestParam(value = "redirect_uri", required = false) String redirect
    ) {
        OAuthDto.GithubTokenResponse response = authService.getAccessToken(clientId, clientSecret, code, redirect);

        return new ResponseDto<>(response);
    }
}
