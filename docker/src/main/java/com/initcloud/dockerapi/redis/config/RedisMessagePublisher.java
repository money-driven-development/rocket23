package com.initcloud.dockerapi.redis.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisMessagePublisher {

	@Getter
	private RedisTemplate<String, String> redisTemplate;

	@Getter
	private ChannelTopic topic;

	public RedisMessagePublisher(RedisTemplate<String, String> redisTemplate, ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	public RedisMessagePublisher(ChannelTopic topic) {
		this.redisTemplate = new RedisTemplate<>();
		this.topic = topic;
	}
}
