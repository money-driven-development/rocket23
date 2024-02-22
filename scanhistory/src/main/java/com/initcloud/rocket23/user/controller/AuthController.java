package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.service.OAuthService;
import com.initcloud.rocket23.user.dto.AuthRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    @PostMapping("/github/login")
    public ResponseDto<Token> githubAuth(@RequestBody AuthRequestDto.githubDto request) {
        Token response = authService.getUserAccessToken(request);

        return new ResponseDto<>(response);
    }

    @Operation(summary = "Get a Local Access Token.", description = "Get a Rocket Access Token")
    @PostMapping("/login")
    public ResponseDto<Token> rocketAuth(@RequestBody AuthRequestDto.loginDto request) {
        Token response =  authService.createUserAccessToken(request);

        return new ResponseDto<>(response);
    }

    @Operation(summary = "Get a Local Access Token.", description = "Get a Rocket Access Token")
    @Parameter(
            name = "X-AUTH-TOKEN",
            in = ParameterIn.HEADER,
            description = "access-token",
            required = true,
            content = @Content(schema = @Schema(type = "string"))
    )
    @Parameter(
            name = "REFRESH-TOKEN",
            in = ParameterIn.HEADER,
            description = "refresh-token",
            required = true,
            content = @Content(schema = @Schema(type = "string"))
    )
    @PostMapping("/refresh")
    public ResponseDto<Token> refreshToken( @RequestHeader(value="X-AUTH-TOKEN") String token,
                                            @RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        Token response = authService.reIssueAccessToken(token, refreshToken);

        return new ResponseDto<>(response);
    }


    @Operation(summary = "Expire Token", description = "Expire Access Token and Refresh Token")
    @PostMapping("/logout")
    public ResponseDto<Boolean> logout( @RequestHeader(value="X-AUTH-TOKEN") String token,
                                      @RequestHeader(value="REFRESH-TOKEN") String refreshToken) {
        authService.logout(token, refreshToken);

        return new ResponseDto<>(true);
    }
}
