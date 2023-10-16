package com.initcloud.dockerapi.container.event;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.initcloud.dockerapi.container.middleware.DockerContainerApi;
import com.initcloud.dockerapi.redis.client.RedisContainerQueueClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("production")
public class ContainerInitializationListener {

	private final DockerContainerApi containerApi;
	private final RedisContainerQueueClient redisQueueClient;

	/**
	 * 앱(스캔 API) 구동 시점에 동작
	 * 컨테이너를 초기화하고 Subscriber 를 실행시킴.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent() {
		this.removeUnControlledContainer(redisQueueClient);

		boolean isNotFull = true;
		while (isNotFull) {
			CreateContainerResponse containerResponse = containerApi.create();
			containerApi.start(containerResponse.getId());
			isNotFull = redisQueueClient.addToQueue(containerResponse.getId());
		}
	}

	/**
	 * 관리를 이탈한 컨테이너를 스캔 API 런타임(구동) 시점에 삭제
	 */
	private void removeUnControlledContainer(RedisContainerQueueClient redisContainerQueueClient) {
		int legacyQueueSize = redisContainerQueueClient.getQueueSize();

		for (int i = 0; i < legacyQueueSize; i++) {
			String containerId = redisContainerQueueClient.pollContainerIdFromQueue();
			containerApi.remove(containerId);
		}
	}
}


