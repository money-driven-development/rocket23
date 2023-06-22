package com.initcloud.dockerapi.redis.client;

import java.util.Map;

import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;
import org.redisson.client.RedisException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.initcloud.dockerapi.container.dto.ContainerDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageStreamConsumer {

	private final RStream<String, ContainerDto> containerStream;

	private String streamKey = "container.docker";
	private String consumerGroupName = "consumerGroupContainer";
	private String consumerName = "consumerContainerDocker";

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
	@Scheduled(fixedDelay = 500)
	public void consumeRedisStream() {
		try {
			containerStream.createGroup(consumerGroupName, StreamMessageId.ALL);
		} catch (RedisException e) {
			log.warn("[EXISTED GROUP] - {}", consumerGroupName);
		}

		Map<StreamMessageId, Map<String, ContainerDto>> messages = containerStream.readGroup(consumerGroupName,
			consumerName);

		for (Map.Entry<StreamMessageId, Map<String, ContainerDto>> entry : messages.entrySet()) {
			Map<String, ContainerDto> msg = entry.getValue();
			log.info("[RECV MESSAGE] - {}", msg);
			containerStream.ack(consumerGroupName, entry.getKey());
		}
	}
}
