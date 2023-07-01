package com.initcloud.rocket23.redis.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum Channels {

	FILE("channel.file");

	@Getter
	private String channel;

	Channels(String channel) {
		this.channel = channel;
	}
}
