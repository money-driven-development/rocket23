package com.initcloud.dockerapi.redis.client;

import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisQueueClient {

	private static final RedisQueueClient redissonQueueClient = new RedisQueueClient("container.standby");

	@Value("${redis.queue.maxsize}")
	private Integer maxQueueSize = 7;
	private RedissonClient redissonClient;
	private RQueue<String> queue;

	public RedisQueueClient(String queue) {
		this.redissonClient = Redisson.create();
		this.queue = this.redissonClient.getQueue(queue);
	}

	/**
	 * 싱글톤 패턴을 통해서 RedisQueueClient 에 대한 단일 인스턴스만 제공
	 */
	public static RedisQueueClient getRedisQueueClient() {
		return redissonQueueClient;
	}

	public Integer getQueueSize() {
		return this.queue.size();
	}

	public Boolean addToQueue(String containerId) {
		if (this.queue.size() == maxQueueSize) {
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
}
