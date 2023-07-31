package com.initcloud.rocket23.security.dto;

import java.time.LocalDateTime;

import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.entity.UserOAuthToken;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubToken extends Token {

	private Long expiresIn;
	private Long refreshTokenExpiresIn;
	private String scope;
	private String tokenType;

	@Builder
	public GithubToken(String accessToken, String refreshToken, Long expiresIn, Long refreshTokenExpiresIn,
		String scope, String tokenType) {
		super(accessToken, refreshToken);
		this.expiresIn = expiresIn;
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
		this.scope = scope;
		this.tokenType = tokenType;
	}

	public static UserOAuthToken toEntity(final GithubToken token, final User user) {
		return UserOAuthToken.builder()
			.createdAt(LocalDateTime.now())
			.modifiedAt(LocalDateTime.now())
			.user(user)
			.tokenType(token.getTokenType())
			.accessToken(token.getAccessToken())
			.refreshToken(token.getRefreshToken())
			.expiresIn(token.getExpiresIn())
			.refreshTokenExpiresIn(token.getRefreshTokenExpiresIn())
			.scope(token.getScope())
			.build();
	}
}
