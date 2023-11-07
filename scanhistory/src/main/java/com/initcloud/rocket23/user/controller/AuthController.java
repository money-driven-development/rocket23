package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.service.OAuthService;
import com.initcloud.rocket23.user.dto.AuthRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth (Login/Register)")
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

    @Operation(
            summary = "Redirect to Github Login page.",
            description = "Redirect to Github Login page to get an auth code.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Redirect response to Github Login page",
                            content = @Content(schema = @Schema(type = "string"))
                    )
            }
    )
    @Parameter(name = "redirect", in = ParameterIn.QUERY, description = "Redirect Url for FE", required = true, schema = @Schema(type = "string"))
    @GetMapping("/github")
    public void githubAuthRedirect(HttpServletResponse response, @RequestParam("redirect") String redirect) {
        authService.redirectGithub(response, redirect);
    }

    @Operation(summary = "Get a Access Token.", description = "Get a Access Token and Redirect to Main Page")
    @PostMapping("/callback")
    public ResponseDto<Token> githubAuth(@RequestBody AuthRequestDto request) {
        Token response = authService.getUserAccessToken(request);

        return new ResponseDto<>(response);
    }
}
