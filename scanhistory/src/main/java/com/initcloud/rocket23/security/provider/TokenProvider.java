package com.initcloud.rocket23.security.provider;

public interface TokenProvider {

	long EXPIRED_TIME_ACCESS = 30 * 60 * 1000; // 30 minutes for access token
	long EXPIRED_TIME_REFRESH = 7 * 24 * 60 * 60 * 1000; // 7 days for refresh token

	boolean validate(String token, String key);
}
