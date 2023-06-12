package com.initcloud.dockerapi.redis.client;

import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisQueueClient {

	private static RedisQueueClient redissonQueueClient = new RedisQueueClient();
	@Value("${redis.queue.maxsize}")
	private Integer maxQueueSize;
	private RedissonClient redissonClient;
	private RQueue<String> queue;

	public RedisQueueClient(String queue) {
		this.redissonClient = Redisson.create();
		this.queue = this.redissonClient.getQueue(queue);
	}

	public static RedisQueueClient getRedisQueueClient() {
		return redissonQueueClient;
	}

	public Integer getQueueSize() {
		return this.queue.size();
	}

	public Boolean addToQueue(String containerid) {
		if (this.queue.size() == maxQueueSize) {
			return false;
		}

		return this.queue.add(containerid);
	}

	public String pollContainerIdFromQueue() {
		return this.queue.poll();
	}

	public Integer adjustMaxQueueSize(int size) {
		return (this.maxQueueSize += size);
	}
}
