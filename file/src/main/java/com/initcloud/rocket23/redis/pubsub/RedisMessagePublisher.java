package com.initcloud.rocket23.redis.pubsub;

import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.dto.RedisFileDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		topicContainer.publish(data);
	}

	public void publishScanMessage(RedisFileDto data) {
		try{
			String jsonMessage = objectMapper.writeValueAsString(data);
			topicScan.publish(jsonMessage);
		} catch (JsonProcessingException e) {
			throw new ApiException(ResponseCode.JSON_PROCESSING_ERROR);
		}
	}
}
