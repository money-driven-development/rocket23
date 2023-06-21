package com.initcloud.dockerapi.redis.client;

import static com.initcloud.dockerapi.redis.client.RedisStreamClient.*;

import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;
import org.springframework.stereotype.Component;

import com.initcloud.dockerapi.redis.message.ScanStreamMessage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisMessageStreamProducer {

	private static final RedisMessageStreamProducer redissonMessageStreamProducer = new RedisMessageStreamProducer();

	private RedisStreamClient redisStreamClient = getRedisStreamClient();

	/**
	 * 싱글톤 패턴을 통해서 RedisMessageStreamProducer 에 대한 단일 인스턴스만 제공
	 */
	public static RedisMessageStreamProducer getMessageStreamProducer() {
		return redissonMessageStreamProducer;
	}

	public void produceMessage(String containerId, ScanStreamMessage message) {
		RStream<String, ScanStreamMessage> stream = redisStreamClient.getRStream();
		StreamMessageId id = stream.add(containerId, message); // Todo - id 로 무엇을 넣을지 파악해야 함.
		log.info("PRODUCE {}", id);
	}
}
