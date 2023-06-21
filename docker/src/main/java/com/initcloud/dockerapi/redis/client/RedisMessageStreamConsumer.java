package com.initcloud.dockerapi.redis.client;

import org.redisson.api.StreamMessageId;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisMessageStreamConsumer {

	private RedisStreamClient client = RedisStreamClient.getRedisStreamClient();
	private String streamKey = "container.docker";
	private String consumerGroupName = "consumerGroupContainer";
	private String consumerName = "consumerContainerDocker";

	private StreamMessageId lastMessageId;

	public RedisMessageStreamConsumer() {
		client.getRStream().createGroup(consumerGroupName, StreamMessageId.ALL);
	}

	/**
	 * Pending 상태의 메시지 Listen
	 */
	public void consumePendingMessage() {

	}

	/**
	 * Pending 상태의 메시지 처리에 대한 응답
	 */
	private void claimMessageProcessing() {

	}

	/**
	 * 메시지 Listen
	 * Todo -
	 */
	public void consumeRedisStream() {
	}

	/**
	 * 메시지 처리에 대한 응답
	 */
	private void ackMessageProcessing(RecordId id) {
	}
}
