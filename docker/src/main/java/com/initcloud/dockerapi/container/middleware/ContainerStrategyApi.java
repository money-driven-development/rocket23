package com.initcloud.dockerapi.container.middleware;

import java.lang.annotation.Annotation;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.initcloud.dockerapi.container.annotation.ContainerLifeCycle;
import com.initcloud.dockerapi.container.aspect.ContainerOrchestrationAspect;
import com.initcloud.dockerapi.container.enums.ContainerLifeCycleStrategy;
import com.initcloud.dockerapi.redis.client.RedisContainerQueueClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContainerStrategyApi {

	private final DockerContainerApi dockerContainerApi;
	private RedisContainerQueueClient redisContainerQueueClient = RedisContainerQueueClient.getRedisQueueClient();

	public void manageStandByContainerByStrategy() {
		Annotation[] annotations = ContainerOrchestrationAspect.class.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof ContainerLifeCycle) {
				ContainerLifeCycle lifeCycle = (ContainerLifeCycle)annotation;
				if (lifeCycle.strategy() == ContainerLifeCycleStrategy.CREATE) {
					manageStandbyContainerByCreateStrategy();
				}
			}
		}
	}

	public void manageCompletedContainerByStrategy(String containerId) {
		Annotation[] annotations = ContainerOrchestrationAspect.class.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof ContainerLifeCycle) {
				ContainerLifeCycle lifeCycle = (ContainerLifeCycle)annotation;
				if (lifeCycle.strategy() == ContainerLifeCycleStrategy.CREATE) {
					manageCompletedContainerByCreateStrategy(containerId);
				}
			}
		}
	}

	/**
	 * [컨테이너 생성 전략]
	 * 스탠바이 컨테이너를 유지하기 위해 컨테이너를 생성함.
	 */
	public void manageStandbyContainerByCreateStrategy() {
		boolean isNotFull = redisContainerQueueClient.canCreateContainer();
		while (isNotFull) {
			CreateContainerResponse containerResponse = dockerContainerApi.create();
			isNotFull = redisContainerQueueClient.addToQueue(containerResponse.getId());
		}
	}

	/**
	 * [컨테이너 생성 전략]
	 * 완료된 컨테이너를 삭제함.
	 */
	public void manageCompletedContainerByCreateStrategy(String containerId) {
		dockerContainerApi.terminate(containerId);
		dockerContainerApi.remove(containerId);
	}

	/**
	 * [컨테이너 재사용 전략]
	 * 완료된 컨테이너를 큐에 추가함.
	 */
	public void manageCompletedContainerByReusableStrategy() {
		// Todo
	}

	/**
	 * [온디맨드 컨테이너 실행 전략]
	 * 완료된 컨테이너를 삭제함.
	 */
	public void manageCompletedContainerByOnDemandCreateStrategy() {
		// Todo
	}
}
