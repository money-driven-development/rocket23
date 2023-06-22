package com.initcloud.dockerapi.redis.config;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.initcloud.dockerapi.redis.enums.Channels;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisPubSubConfig {

	private final RedissonClient redissonClient;

	@Bean
	public RTopic topicScan() {
		RTopic topic = redissonClient.getTopic(Channels.SCAN.getChannel());
		return topic;
	}

	@Bean
	public RTopic topicContainer() {
		RTopic topic = redissonClient.getTopic(Channels.CONTAINER.getChannel());
		return topic;
	}
}
