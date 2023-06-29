package com.initcloud.rocket23.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisClientConfig {
	// Todo - setAddress -> 환경 변수로 옮길 예정
	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.setCodec(new JsonJacksonCodec())
			.useSingleServer()
			.setAddress("redis://127.0.0.1:6379");
		return Redisson.create(config);
	}
}
