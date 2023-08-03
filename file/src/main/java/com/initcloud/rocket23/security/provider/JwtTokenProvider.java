package com.initcloud.rocket23.security.provider;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.dto.UsernameToken;
import com.initcloud.rocket23.security.service.CustomUserDetailService;
import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.enums.RoleType;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
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

	public Authentication getAuthentication(String token, String key) {

		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token, key));

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public boolean validate(String token, String key) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (SecurityException e) {
			log.error(ResponseCode.INVALID_TOKEN_SIGNATURE.getMessage(), e.getMessage());
			throw new ApiAuthException(ResponseCode.INVALID_TOKEN_SIGNATURE);
		} catch (MalformedJwtException e) {
			log.error(ResponseCode.INVALID_TOKEN.getMessage(), e.getMessage());
			throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			log.error(ResponseCode.TOKEN_EXPIRED.getMessage(), e.getMessage());
			throw new ApiAuthException(ResponseCode.TOKEN_EXPIRED);
		} catch (UnsupportedJwtException e) {
			log.error(ResponseCode.UNSUPPORTED_TOKEN.getMessage(), e.getMessage());
			throw new ApiAuthException(ResponseCode.UNSUPPORTED_TOKEN);
		} catch (IllegalArgumentException e) {
			log.error(ResponseCode.EMPTY_TOKEN_CLAIMS.getMessage(), e.getMessage());
			throw new ApiAuthException(ResponseCode.EMPTY_TOKEN_CLAIMS);
		} catch (Exception e) {
			log.error(ResponseCode.INVALID_TOKEN.getMessage(), e.getMessage());
			throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
		}
	}

	public String getToken() {
		String username = getUsername();

		User requestUser = (User)userDetailsService.loadUserByUsername(username);

		return requestUser.getOAuthToken().getAccessToken();
	}

	public String resolve(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");

		if (authorization == null)
			return null;

		if (authorization.startsWith("Bearer "))
			return authorization.substring(7);

		if (authorization.startsWith("token "))
			return authorization.substring(6);

		throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
	}

}

