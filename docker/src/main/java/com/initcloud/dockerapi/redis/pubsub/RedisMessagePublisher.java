package com.initcloud.dockerapi.redis.pubsub;

import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

	@Qualifier("topicScan")
	private final RTopic topicScan;

	@Qualifier("topicContainer")
	private final RTopic topicContainer;

	public void publishContainerMessage(String data) {
		topicContainer.publish(data);
	}

	public void publishScanMessage(String data) {
		topicScan.publish(data);
	}
}
