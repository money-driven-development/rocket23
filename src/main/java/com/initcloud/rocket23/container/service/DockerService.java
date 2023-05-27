package com.initcloud.rocket23.container.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class DockerService implements ContainerService {

	/**
	 * 도커 컨테이너를 실행. 스캔 명령이 함께 동작.
	 * @param count 만큼 컨테이너 실행.
	 * count 가 null 일 경우 단일(1개) 실행.
	 */
	@Override
	public void executeContainer(Integer count) {

	}

	/**
	 * 도커 컨테이너를 실행 후 대기.
	 * @param count 만큼 컨테이너 실행
	 * count 가 null 일 경우 단일(1개) 실행.
	 */
	@Override
	public void executeContainerForStandBy(@Nullable Integer count) {

	}

	/**
	 * 도커 컨테이너를 종료.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public void terminateContainer(String containerId) {

	}

	/**
	 * 도커 컨테이너 정보를 조회.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public void getContainer(String containerId) {

	}

	/**
	 * 도커 컨테이너 목록을 조회.
	 */
	@Override
	public void getContainerList() {

	}

	/**
	 * 도커 컨테이너을 일시정지.
	 * @param containerId 를 대상으로 함.
	 */
	@Override
	public void pauseContainer(String containerId) {

	}
}
