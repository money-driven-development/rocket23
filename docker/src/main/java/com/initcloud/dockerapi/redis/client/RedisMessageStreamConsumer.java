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
	 * Pending 상태의 메시지 Listen
	 */
	public void consumePendingMessage() {

	}

	/**
	 * 메시지 Listen
	 */
	@Override
	public void onMessage(MapRecord<String, Object, Object> message) {
		RecordId messageId = message.getId();
		Map<Object, Object> body = message.getValue();

		/** Todo - 스캔 로직 구현 이후 추가
		 * 1. Redis Queue 에서 containerId를 pop.
		 * 2. 해당 컨테이너를 동작시킴
		 */
	}
}
