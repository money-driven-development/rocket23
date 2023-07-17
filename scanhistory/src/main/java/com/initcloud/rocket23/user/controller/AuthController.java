package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;

import javax.servlet.http.HttpServletResponse;

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


	public void githubAuthRedirect(HttpServletResponse response, @RequestParam("redirect") String redirect) {
		authService.redirectGithub(response, redirect);
	}

	@GetMapping("/callback")
	public ResponseDto<Token> githubAuth(@RequestParam("code") String authCode) {
		Token response = authService.getUserAccessToken(authCode);

		return new ResponseDto<>(response);
	}
}
