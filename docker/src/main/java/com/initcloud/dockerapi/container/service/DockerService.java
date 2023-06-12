package com.initcloud.dockerapi.container.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.initcloud.dockerapi.container.dto.ContainerInspectDto;
import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.client.DockerContainerApi;
import com.initcloud.dockerapi.container.enums.ContainerAPIType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DockerService implements ContainerService {

	private final DockerContainerApi dockerContainerApi;

	/**
	 * 도커 컨테이너를 실행. 스캔 명령이 함께 동작.
	 * @param count 만큼 컨테이너 실행.
	 * count 가 null 일 경우 단일(1개) 실행.
	 */
	@Override
	public ContainerDto executeContainer(Integer count) {
		CreateContainerResponse containerResponse = dockerContainerApi.create();

		return new ContainerDto(containerResponse);
	}

	/**
	 * 도커 컨테이너를 실행 후 대기.
	 * @param count 만큼 컨테이너 실행
	 * count 가 null 일 경우 단일(1개) 실행.
	 */
	@Override
	public ContainerDto executeContainerForStandBy(@Nullable Integer count) {
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
		InspectContainerResponse containerResponse = dockerContainerApi.inspect(containerId);

		return (containerResponse == null);
	}

	/**
	 * 도커 컨테이너 정보를 조회.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public ContainerInspectDto getContainerDetails(String containerId) {
		InspectContainerResponse containerResponse = dockerContainerApi.inspect(containerId);

		return new ContainerInspectDto(containerResponse, ContainerAPIType.DOCKER);
	}

	/**
	 * 전체 도커 컨테이너 목록을 조회.
	 */
	@Override
	public List<ContainerDto> getContainerList() {
		List<Container> containers = dockerContainerApi.get();

		return containers.stream()
			.map(ContainerDto::new)
			.collect(Collectors.toList());
	}

	/**
	 * 도커 컨테이너을 일시정지.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public Boolean pauseContainer(String containerId) {
		dockerContainerApi.stop(containerId);
		InspectContainerResponse containerResponse = dockerContainerApi.inspect(containerId);

		return containerResponse.getState().getPaused();
	}
}
