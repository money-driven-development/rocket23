package com.initcloud.rocket23.infra.redis.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

	@Qualifier("topicScan")
	private final RTopic topicScan;

	@Qualifier("topicContainer")
	private final RTopic topicContainer;

	private final ObjectMapper objectMapper;

	public void publishContainerMessage(String data) {
		try {
			String jsonMessage = objectMapper.writeValueAsString(data);

			if (!data.isEmpty()) {
				topicContainer.publish(jsonMessage);
			}
		} catch (JsonProcessingException e) {
			// Todo - 이 예외는 응답으로 반환할 것이 아니므로 별도의 처리가 필요.
			throw new ApiException(ResponseCode.INVALID_REQUEST);
		}
	}

	public void publishScanMessage(String data) {
		if (!data.isEmpty()) {
			topicScan.publish(data);
		}
	}
}
