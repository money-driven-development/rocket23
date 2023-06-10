package com.initcloud.dockerapi.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

	private final RedisTemplate<String, String> redisTemplate;

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(new RedisMessageSubscriber());
	}

	@Bean
	RedisMessagePublisher redisPublisher() {
		return new RedisMessagePublisher(redisTemplate, topic());
	}

	@Bean
	ChannelTopic topic() {
		return new ChannelTopic("default");
	}

	@Bean
	RedisMessageListenerContainer redisContainer() {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisTemplate.getConnectionFactory());
		container.addMessageListener(messageListener(), topic());
		return container;
	}
}
