package com.initcloud.dockerapi.redis.client;

import static com.initcloud.dockerapi.redis.client.RedisStreamClient.*;

import org.redisson.api.RStream;

import com.initcloud.dockerapi.redis.message.StreamMessage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisMessageStreamProducer {

	private static final RedisMessageStreamProducer redissonMessageStreamPublisher = new RedisMessageStreamProducer();

	private RedisStreamClient redisStreamClient = getRedisStreamClient();

	/**
	 * 싱글톤 패턴을 통해서 RedisMessageStreamProducer 에 대한 단일 인스턴스만 제공
	 */
	public static RedisMessageStreamProducer getMessageStreamPublisher() {
		return redissonMessageStreamPublisher;
	}

	public void produceMessage(StreamMessage message) {
		RStream<String, StreamMessage> stream = redisStreamClient.getRStream();
		stream.add(message.getDirectory(), message); // Todo - id 로 무엇을 넣을지 파악해야 함.
	}
}
