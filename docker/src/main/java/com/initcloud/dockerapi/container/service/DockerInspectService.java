package com.initcloud.dockerapi.container.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.dto.ContainerInspectDto;
import com.initcloud.dockerapi.container.enums.ContainerAPIType;
import com.initcloud.dockerapi.container.middleware.DockerContainerApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DockerInspectService implements ContainerInspectService {

	private final DockerContainerApi dockerContainerApi;

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
	 * 도커 컨테이너의 자원 상태 정보를 조회
	 * @return
	 */
	@Override
	public Object getContainerMetrics(String containerId) {
		return null;
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
}
