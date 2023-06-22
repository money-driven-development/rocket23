package com.initcloud.dockerapi.redis.config;

import java.awt.*;

import org.redisson.Redisson;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.redis.message.ScanStreamMessage;

@Configuration
public class RedisConfig {

	@Bean
	public RedissonClient redissonClient() {
		return Redisson.create();
	}

	@Bean
	public RStream<String, ScanStreamMessage> redisScanStream(RedissonClient redissonClient) {
		return redissonClient.getStream("container.scan");
	}

	@Bean
	public RStream<String, ContainerDto> redisContainerStream(RedissonClient redissonClient) {
		return redissonClient.getStream("container.docker");
	}
}
