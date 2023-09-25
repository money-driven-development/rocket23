package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.security.dto.OAuthDto;
import com.initcloud.rocket23.security.service.OAuthService;
import com.initcloud.rocket23.user.dto.AuthRequestDto;
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
    @PostMapping("/callback")
    public ResponseDto<OAuthDto.GithubTokenResponse> githubAuth(@RequestBody AuthRequestDto request) {
        OAuthDto.GithubTokenResponse response = authService.getAccessToken(request);

        return new ResponseDto<>(response);
    }
}
