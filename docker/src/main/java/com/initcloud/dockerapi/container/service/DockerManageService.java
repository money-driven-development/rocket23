package com.initcloud.dockerapi.container.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.dto.IaCScanRequestDto;
import com.initcloud.dockerapi.container.middleware.DockerContainerApi;
import com.initcloud.dockerapi.redis.client.RedisContainerQueueClient;
import com.initcloud.dockerapi.redis.pubsub.RedisMessagePublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DockerManageService implements ContainerManageService {

	private final DockerContainerApi dockerContainerApi;
	private final RedisMessagePublisher publisher;
	private static RedisContainerQueueClient queueClient = RedisContainerQueueClient.getRedisQueueClient();

	/**
	 * 도커 컨테이너를 실행. 스캔 명령이 함께 동작.
	 * @param count 만큼 컨테이너 실행.
	 * count 가 null 일 경우 단일(1개) 실행.
	 */
	@Override
	public ContainerDto executeContainer(Integer count, IaCScanRequestDto path) {
		CreateContainerResponse containerResponse = dockerContainerApi.create();
		dockerContainerApi.start(containerResponse.getId());

		String containerId = queueClient.pollContainerIdFromQueue();
		queueClient.addToQueue(containerResponse.getId());

		String scanResult = dockerContainerApi.execute(containerId, path);
		publisher.publishScanMessage(scanResult);
		dockerContainerApi.stop(containerId);

		return new ContainerDto(containerId, "Exited");
	}

	/**
	 * 도커 컨테이너를 생성.
	 * @param count 만큼 컨테이너를 생성하며 실행하지 않음.
	 * count 가 null 일 경우 단일(1개) 생성.
	 */
	@Override
	public ContainerDto createContainerForStandBy(@Nullable Integer count) {
		CreateContainerResponse containerResponse = dockerContainerApi.create();

		return new ContainerDto(containerResponse);
	}

	/**
	 * 도커 컨테이너를 종료.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public Boolean terminateContainer(String containerId) {
		dockerContainerApi.terminate(containerId);
		dockerContainerApi.remove(containerId);

		InspectContainerResponse containerResponse = dockerContainerApi.inspect(containerId);

		return (containerResponse == null);
	}

	/**
	 * 도커 컨테이너를 일시정지.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public Boolean pauseContainer(String containerId) {
		dockerContainerApi.stop(containerId);
		InspectContainerResponse containerResponse = dockerContainerApi.inspect(containerId);

		return containerResponse.getState().getPaused();
	}
}
