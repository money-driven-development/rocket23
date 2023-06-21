package com.initcloud.dockerapi.redis.client;

import org.redisson.Redisson;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;

import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.InitializingBean;

import com.initcloud.dockerapi.redis.message.ScanStreamMessage;

import lombok.Getter;

public class RedisStreamClient implements InitializingBean {

	private static final String STREAM_KEY = "container.docker";
	private static final RedisStreamClient redisStreamClient = new RedisStreamClient();

	@Getter
	private RedissonClient redissonClient;

	@Getter
	private RStream<String, ScanStreamMessage> rStream;

	public RedisStreamClient() {
		this.redissonClient = Redisson.create();
		this.rStream = redissonClient.getStream(STREAM_KEY, StringCodec.INSTANCE);
	}

	/**
	 * 싱글톤 패턴을 통해서 RedisStreamClient 에 대한 단일 인스턴스만 제공
	 */
	public static RedisStreamClient getRedisStreamClient() {
		return redisStreamClient;
	}

	@Override
	public void afterPropertiesSet() {
		this.rStream = redissonClient.getStream(STREAM_KEY, StringCodec.INSTANCE);
	}
}
