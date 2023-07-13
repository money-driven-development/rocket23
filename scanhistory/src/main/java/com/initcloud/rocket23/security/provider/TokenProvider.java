package com.initcloud.rocket23.security.provider;

public interface TokenProvider {

	long EXPIREDTIME = 3 * 24 * 60 * 60 * 1000L;

	boolean validate(String token, String key);
}
