package com.initcloud.dockerapi.redis.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisContainerQueueClient {

	private static final RedisContainerQueueClient redissonQueueClient = new RedisContainerQueueClient(
		"container.standby");

	@Value("${redis.queue.maxsize}")
	private Integer maxQueueSize = 7;

	@Value("${spring.redis.host}")
	private String redisHost = "localhost";

	@Value("${spring.redis.port}")
	private int redisPort = 6379;

	@Value("${spring.redis.password}")
	private String redisPassword = "password";
	private RedissonClient redissonClient;
	private RQueue<String> queue;

	public RedisContainerQueueClient(String queue) {
		Config config = new Config();
		config.setCodec(new JsonJacksonCodec())
				.useSingleServer()
				.setAddress("redis://" + redisHost + ":" + Integer.toString(redisPort))
				.setPassword(redisPassword);
		this.redissonClient = Redisson.create(config);
		this.queue = this.redissonClient.getQueue(queue);
	}

	/**
	 * 싱글톤 패턴을 통해서 RedisContainerQueueClient 에 대한 단일 인스턴스만 제공
	 */
	public static RedisContainerQueueClient getRedisQueueClient() {
		return redissonQueueClient;
	}

	public Integer getQueueSize() {
		return this.queue.size();
	}

	public boolean canCreateContainer() {
		return this.queue.size() < maxQueueSize;
	}

	public Boolean addToQueue(String containerId) {
		if (this.queue.size() >= maxQueueSize) {
			return false;
		}

		return this.queue.add(containerId);
	}

	public String pollContainerIdFromQueue() {
		return this.queue.poll();
	}

	public Integer adjustMaxQueueSize(int size) {
		return (this.maxQueueSize += size);
	}

	public void shutDown() {
		redissonClient.shutdown();
	}
}
