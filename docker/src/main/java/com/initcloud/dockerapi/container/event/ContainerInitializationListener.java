package com.initcloud.dockerapi.container.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.initcloud.dockerapi.container.middleware.DockerContainerApi;
import com.initcloud.dockerapi.redis.client.RedisQueueClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContainerInitializationListener {

	private final DockerContainerApi containerApi;

	/**
	 * 앱 구동 완료 후 동작
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent() {
		RedisQueueClient redisQueueClient = RedisQueueClient.getRedisQueueClient();

		boolean isNotFull = true;
		while(isNotFull) {
			CreateContainerResponse containerResponse = containerApi.create();
			isNotFull = redisQueueClient.addToQueue(containerResponse.getId());
		}
	}
}


