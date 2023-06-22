package com.initcloud.dockerapi.container.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.redis.client.RedisMessageStreamProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageStreamAspect {

	private final RedisMessageStreamProducer producer;

	@AfterReturning(value = "execution(* com.initcloud.dockerapi.container.service.DockerManageService.executeContainer(..))", returning = "containerDto")
	public void produceMessageAfterReturningContainerExecute(final ContainerDto containerDto) {
		producer.produceMessage(containerDto);
	}
}
