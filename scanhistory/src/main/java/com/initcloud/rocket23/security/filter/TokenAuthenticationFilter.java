package com.initcloud.rocket23.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.security.config.SecurityProperties;
import com.initcloud.rocket23.security.provider.JwtProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtTokenProvider;
	private final SecurityProperties securityProperties;

	private static final String EXCEPTION = "exception";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException, ServletException {
		String token = jwtTokenProvider.resolve(request);
		jwtTokenProvider.validate(token, securityProperties.getSecret());

		try {
			Authentication authentication = jwtTokenProvider.getAuthentication(token,
				securityProperties.getSecret());
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (NullPointerException e) {
			request.setAttribute(EXCEPTION, ResponseCode.NULL_TOKEN.getCode());
		} catch (SecurityException e) {
			request.setAttribute(EXCEPTION, ResponseCode.INVALID_TOKEN_SIGNATURE.getCode());
		} catch (MalformedJwtException e) {
			request.setAttribute(EXCEPTION, ResponseCode.INVALID_TOKEN_FORMAT.getCode());
		} catch (ExpiredJwtException e) {
			request.setAttribute(EXCEPTION, ResponseCode.TOKEN_EXPIRED.getCode());
		} catch (UnsupportedJwtException e) {
			request.setAttribute(EXCEPTION, ResponseCode.UNSUPPORTED_TOKEN.getCode());
		} catch (IllegalArgumentException e) {
			request.setAttribute(EXCEPTION, ResponseCode.EMPTY_TOKEN_CLAIMS.getCode());
		} catch (Exception e) {
			request.setAttribute(EXCEPTION, ResponseCode.INVALID_TOKEN.getCode());
		}

		chain.doFilter(request, response);
	}
}

