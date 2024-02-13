package com.initcloud.dockerapi.container.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.initcloud.dockerapi.container.enums.IaCType;
import com.initcloud.dockerapi.redis.message.ProjectUploadMessage;
import org.json.simple.parser.ParseException;
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
	private final RedisContainerQueueClient queueClient;

	/**
	 * 도커 컨테이너를 실행. 스캔 명령이 함께 동작.
	 * @param count 만큼 컨테이너 실행.
	 * count 가 null 일 경우 단일(1개) 실행.
	 */
	@Override
	public ContainerDto executeContainer(Integer count, IaCScanRequestDto path)
			throws ParseException, JsonProcessingException {
		try {
			CreateContainerResponse containerResponse = dockerContainerApi.create(); // 스캔을 수행할 컨테이너 생성 (cold start)
			dockerContainerApi.start(containerResponse.getId()); // 컨테이너 실행

			// 미리 생성해둔 스탠바이 컨테이너를 사용해서 cold start 시간을 줄일 수 있음
			String containerId = queueClient.pollContainerIdFromQueue();
			queueClient.addToQueue(containerResponse.getId());

			// 앞서서 가져온 ID를 가지는 컨테이너로 통해서 스캔 수행
			String scanResult = dockerContainerApi.execute(containerResponse.getId(), path);

			// 이걸 다른 곳에 쏨(Main 컴포넌트로 전달하거나 여기서 바로 DB에 저장해도 OK)
			publisher.publishScanMessage(scanResult, path.getTeamCode(), path.getProjectCode(), path.getIacPath());

			//

			/** 스캔이 완료된 컨테이너 종료 시킴
			 * Todo
			 * com.initcloud.dockerapi.container.middleware.ContainerStrategyApi
			 * com.initcloud.dockerapi.container.enums.ContainerLifeCycleStrategy
			 * 위 두 개의 컨테이너 관리 정책에 따라서, 컨테이너를 stop 할지 혹은 대기열에 다시 넣을지 등을 결정할 수 있음.
			 */
			//dockerContainerApi.stop(containerId);
			// 동작시켰던 컨테이너 정보를 반환
			return new ContainerDto(containerId, "Exited");
		}catch(Exception e){
			path.updateError();
			publisher.publishScanMessage(null, path.getTeamCode(), path.getProjectCode(), path.getIacPath());
		}

		return null;
	}

	public ContainerDto executeContainer(ProjectUploadMessage upload) throws ParseException, JsonProcessingException {
		IaCScanRequestDto request = new IaCScanRequestDto(IaCType.TERRAFORM,
				upload.getUuid(),
				upload.getTeam(),
				upload.getProject()); // Todo - 나중에 타입을 요청 별로 바꿔야 함 ㅎㅎ.
		return this.executeContainer(1, request);
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
