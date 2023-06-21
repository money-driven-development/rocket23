package com.initcloud.dockerapi.container.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.initcloud.dockerapi.redis.client.RedisMessageStreamProducer;
import com.initcloud.dockerapi.redis.message.StreamMessage;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class MessageStreamAspect {

	private RedisMessageStreamProducer producer = RedisMessageStreamProducer.getMessageStreamProducer();

	@AfterReturning(value = "execution(* com.initcloud.dockerapi.container.service.DockerManageService.executeContainer(..)) && args(containerId, message))")
	public void produceMessageAfterReturningContainerExecute(String containerId, StreamMessage message) {
		producer.produceMessage(containerId, message);
	}
}
