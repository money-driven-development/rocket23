package com.initcloud.dockerapi.redis.pubsub;

import javax.annotation.PostConstruct;

import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;

import io.netty.handler.codec.DecoderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageSubscriber {

	@Qualifier("topicScan")
	private final RTopic topicScan;

	@Qualifier("topicContainer")
	private final RTopic topicContainer;

	@PostConstruct
	public void initialize() {
		subscribeScanChannel();
	}

	public void subscribeScanChannel() {
		log.info("[Subscribe Scan Channel]");
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
}
