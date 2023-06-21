package com.initcloud.dockerapi.container.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.initcloud.dockerapi.container.middleware.DockerContainerApi;
import com.initcloud.dockerapi.redis.client.RedisContainerQueueClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContainerInitializationListener {

	private final DockerContainerApi containerApi;

	/**
	 * 앱 구동 완료 후 동작
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent() {
		RedisContainerQueueClient redisContainerQueueClient = RedisContainerQueueClient.getRedisQueueClient();
		removeUnControlledContainer(redisContainerQueueClient);

		boolean isNotFull = true;
		while (isNotFull) {
			CreateContainerResponse containerResponse = containerApi.create();
			isNotFull = redisContainerQueueClient.addToQueue(containerResponse.getId());
			log.info("[Init Containers] - {} {}", containerResponse.getId(), isNotFull);
		}

		redisContainerQueueClient.shutDown();
	}

	/**
	 * 관리를 이탈한 컨테이너를 런타임 시점에 삭제
	 */
	private void removeUnControlledContainer(RedisContainerQueueClient redisContainerQueueClient) {
		int legacyQueueSize = redisContainerQueueClient.getQueueSize();

		for(int i = 0 ; i < legacyQueueSize ; i++) {
			String containerId = redisContainerQueueClient.pollContainerIdFromQueue();
			containerApi.remove(containerId);
		}
	}
}


