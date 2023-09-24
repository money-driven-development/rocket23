package com.initcloud.dockerapi.redis.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.dockerapi.container.service.DockerManageService;
import com.initcloud.dockerapi.redis.message.ProjectUploadMessage;
import io.netty.handler.codec.DecoderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageSubscriber {

	@Qualifier("topicScan")
	private final RTopic topicScan;

	@Qualifier("topicContainer")
	private final RTopic topicContainer;

	@Qualifier("topicProject")
	private final RTopic topicProject;

	private final ObjectMapper objectMapper;

	private final DockerManageService dockerManageService;

	@PostConstruct
	public void initialize() {
		subscribeScanChannel();
	}

	public void subscribeScanChannel() {
		log.info("[SUBSCRIBE] - Scan Channel");
		topicScan.addListener(String.class, (channel, data) -> {
			try {
				log.info("[RECEIVED] {} - {}", channel, data);
			} catch (DecoderException e) {
				log.warn("[DECODE ERROR] - from {}, about {}", channel, e.getMessage());
			} catch (Exception e) {
				log.warn("[DATA BIND ERROR] - from {}, about {}", channel, e.getMessage());
			}
		});
	}

	public void subscribeProjectChannel() {
		log.info("[SUBSCRIBE] - Project Channel");
		topicProject.addListener(String.class, (channel, data) -> {
			try {
				log.info("[RECEIVED] {} - {}", channel, data);
				ProjectUploadMessage projectUploadMessage = objectMapper.readValue(data, ProjectUploadMessage.class);
				dockerManageService.executeContainer(projectUploadMessage);
			} catch (DecoderException e) {
				log.warn("[DECODE ERROR] - from {}, about {}", channel, e.getMessage());
			} catch (Exception e) {
				log.warn("[DATA BIND ERROR] - from {}, about {}", channel, e.getMessage());
			}
		});
	}
}
