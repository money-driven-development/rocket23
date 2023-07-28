package com.initcloud.rocket23.security.provider;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.dto.UsernameToken;
import com.initcloud.rocket23.security.service.CustomUserDetailService;
import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.enums.RoleType;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private static final long EXPIREDTIME = 3 * 24 * 60 * 60 * 1000L;

	private final CustomUserDetailService userDetailsService;

	public Token create(String username, RoleType role, String key) {

		Date now = new Date();

		String accessToken = Jwts.builder()
			.setSubject(username)
			.claim("role", role)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + EXPIREDTIME))
			.signWith(SignatureAlgorithm.HS256, key)
			.compact();

		return new UsernameToken(username, accessToken, null);
	}

	public String getUsername(String token, String key) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
	}

	public String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((User)principal).getUsername();
	}

	public String getToken() {
		String username = getUsername();

		User requestUser = (User)userDetailsService.loadUserByUsername(username);

		return requestUser.getOAuthToken().getAccessToken();
	}

}

