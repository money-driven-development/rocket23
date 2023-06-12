package com.initcloud.dockerapi.container.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.initcloud.dockerapi.container.client.DockerContainerApi;
import com.initcloud.dockerapi.redis.client.RedisQueueClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ContainerOrchestrationAspect {

	private final DockerContainerApi dockerContainerApi;
	private RedisQueueClient redisQueueClient = RedisQueueClient.getRedisQueueClient();

	/**
	 * 컨테이너 동작 후 스탠바이 컨테이너 수 조정
	 */
	@AfterReturning(value = "execution(* com.initcloud.dockerapi.container.service.DockerService.executeContainer(..)) && args(containerId))")
	public void afterReturningContainerExecute(String containerId) {
		log.info("[SCAN FINISHED] {}", containerId);

		redisQueueClient.addToQueue(containerId);
	}

	/**
	 * 컨테이너 종료 후 스탠바이 컨테이너 수 조정
	 */
	@AfterReturning(value = "execution(* com.initcloud.dockerapi.container.service.DockerService.terminateContainer(..)) && args(containerId))")
	public void afterReturningContainerTerminate(String containerId) {
		log.info("[CONTAINER TERMINATED] {}", containerId);

		CreateContainerResponse newContainer = dockerContainerApi.create();
		redisQueueClient.addToQueue(newContainer.getId());
	}
}
