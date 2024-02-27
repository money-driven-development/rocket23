package com.initcloud.rocket23.security.provider;

public interface TokenProvider {

	long MINUTE = 6 * 30;
	long DAY = 24 * 60 * MINUTE ;

	long EXPIRED_TIME_ACCESS = 30 * MINUTE; // 30 minutes for access token
	long EXPIRED_TIME_REFRESH = 7 * DAY; // 7 days for refresh token

	boolean validate(String token, String key);
}
