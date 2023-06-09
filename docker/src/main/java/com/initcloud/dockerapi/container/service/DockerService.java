package com.initcloud.dockerapi.container.service;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.initcloud.dockerapi.container.dto.DockerDto;
import com.initcloud.dockerapi.container.middleware.DockerContainerApi;

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
	public Void executeContainer(Integer count) {
		dockerContainerApi.create();

		return null;
	}

	/**
	 * 도커 컨테이너를 실행 후 대기.
	 * @param count 만큼 컨테이너 실행
	 * count 가 null 일 경우 단일(1개) 실행.
	 */
	@Override
	public Void executeContainerForStandBy(@Nullable Integer count) {
		dockerContainerApi.create();

		return null;
	}

	/**
	 * 도커 컨테이너를 종료.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public Void terminateContainer(String containerId) {
		dockerContainerApi.terminate(containerId);

		return null;
	}

	/**
	 * 도커 컨테이너 정보를 조회.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public DockerDto.Container getContainerDetails(String containerId) {
		dockerContainerApi.inspect(containerId);

		return null;
	}

	/**
	 * 전체 도커 컨테이너 목록을 조회.
	 */
	@Override
	public List<Object> getContainerList() {
		dockerContainerApi.get();

		return null;
	}

	/**
	 * 도커 컨테이너을 일시정지.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public Void pauseContainer(String containerId) {
		dockerContainerApi.stop(containerId);

		return null;
	}
}
