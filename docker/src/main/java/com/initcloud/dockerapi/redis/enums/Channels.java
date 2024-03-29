package com.initcloud.dockerapi.redis.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum Channels {

	CONTAINER("channel.container"),
	SCAN("channel.scan");

	@Getter
	private String channel;

	Channels(String channel) {
		this.channel = channel;
	}
}