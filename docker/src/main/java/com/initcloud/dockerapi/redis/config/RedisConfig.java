package com.initcloud.dockerapi.redis.config;

import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.topic}")
	private String topic;

	private final RedisTemplate<String, String> redisTemplate;

	@Bean
	ChannelTopic topic() {
		return new ChannelTopic(topic);
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new RedissonConnectionFactory();
	}
}
