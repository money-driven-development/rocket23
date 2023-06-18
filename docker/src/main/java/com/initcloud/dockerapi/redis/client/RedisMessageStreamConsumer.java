package com.initcloud.dockerapi.redis.client;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageStreamConsumer implements StreamListener<String, MapRecord<String, Object, Object>> {

	private StreamMessageListenerContainer<String, MapRecord<String, Object, Object>> streamMessageListenerContainer;
	private RedisStreamClient client = RedisStreamClient.getRedisStreamClient();
	private String streamKey = "container.docker";
	private String consumerGroupName = "consumerGroupContainer";
	private String consumerName = "consumerContainerDocker";

	public RedisMessageStreamConsumer() {
	}

	/**
	 * 메시지 Listen
	 */
	@PostConstruct
	public void consumeMessage() {
		// Todo - 컨슈머 구현
	}

	/**
	 * 메시지 처리에 대한 응답
	 */
	public void ackMessageProcessing() {

	}

	/**
	 * Pending 상태의 메시지 Listen
	 */
	public void consumePendingMessage() {

	}

	/**
	 * Pending 상태의 메시지 처리에 대한 응답
	 */
	public void claimMessageProcessing() {

	}

	@Override
	public void onMessage(MapRecord<String, Object, Object> message) {
		RecordId messageId = message.getId();
		Map<Object, Object> body = message.getValue();

		// Todo - 처리로직 추가
	}
}
