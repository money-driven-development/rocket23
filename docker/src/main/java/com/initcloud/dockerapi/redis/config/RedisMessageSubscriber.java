package com.initcloud.dockerapi.redis.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisMessageSubscriber implements MessageListener {

	@Getter
	private final List<String> messageList = new ArrayList<>();

	@Override
	public void onMessage(final Message message, final byte[] pattern) {
		messageList.add(message.toString());

		log.info("[Message RECV] CH.{} - {}", new String(message.getChannel()), new String(message.getBody()));
	}
}
