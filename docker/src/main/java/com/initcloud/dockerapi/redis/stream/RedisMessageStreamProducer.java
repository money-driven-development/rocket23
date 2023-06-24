package com.initcloud.dockerapi.redis.stream;

import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;
import org.springframework.stereotype.Component;

import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.redis.message.ScanStreamMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageStreamProducer {

	private final RStream<String, ScanStreamMessage> scanStream;
	private final RStream<String, ContainerDto> containerStream;

	/**
	 * 스캔 종료 후
	 */
	public void produceMessage(String containerId, ScanStreamMessage message) {
		StreamMessageId id = scanStream.add(containerId, message); // Todo - id 로 무엇을 넣을지 파악해야 함.
		log.info("[SCAN] {}", id);
	}

	/**
	 * 스탠바이 컨테이너 실행 후
	 */
	public void produceMessage(ContainerDto container) {
		StreamMessageId id = containerStream.add(container.getContainerId(), container);
		log.info("[STANDBY] {}", id);
	}
}
