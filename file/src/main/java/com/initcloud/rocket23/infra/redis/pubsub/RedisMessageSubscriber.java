package com.initcloud.rocket23.infra.redis.pubsub;

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

	@Qualifier("topicFile")
	private final RTopic topicFile;

	@PostConstruct
	public void initialize() {
		subscribeFileChannel();
	}

	public void subscribeFileChannel() {
		log.info("[Subscribe File Channel]");
		topicFile.addListener(String.class, (channel, data) -> {
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
