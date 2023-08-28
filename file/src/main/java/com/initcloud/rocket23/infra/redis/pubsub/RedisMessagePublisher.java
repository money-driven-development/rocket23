package com.initcloud.rocket23.infra.redis.pubsub;

import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.project.dto.RedisFileDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

	@Qualifier("topicFile")
	private final RTopic topicFile;

	private final ObjectMapper objectMapper;

	public void publishFileMessage(RedisFileDto data) {
		try {
			String jsonMessage = objectMapper.writeValueAsString(data);
			topicFile.publish(jsonMessage);
		} catch (JsonProcessingException e) {
			throw new ApiException(ResponseCode.JSON_PROCESSING_ERROR);
		}
	}
}
