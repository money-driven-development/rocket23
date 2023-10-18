package com.initcloud.rocket23.redis.client;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisContainerQueueClient {

    private static Integer maxQueueSize = 7;

    private final RedissonClient redissonClient;
    private final RQueue<String> queue;

    public RedisContainerQueueClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        this.queue = this.redissonClient.getQueue("container.standby");
    }

    /**
     * 싱글톤 패턴을 통해서 RedisContainerQueueClient 에 대한 단일 인스턴스만 제공
     */
//	public static RedisContainerQueueClient getRedisQueueClient() {
//		return redissonQueueClient;
//	}
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
