package com.initcloud.rocket23.container.service;

import org.springframework.stereotype.Service;

@Service
public class DockerService implements ContainerService {

	/**
	 * 도커 컨테이너를 실행.
	 */
	@Override
	public void executeContainer() {

	}

	/**
	 * 도커 컨테이너를 대기상태로 실행.
	 */
	@Override
	public void executeContainerForStandBy() {

	}

	/**
	 * 도커 컨테이너를 종료.
	 */
	@Override
	public void terminateContainer() {

	}

	/**
	 * 도커 컨테이너 정보를 조회.
	 */
	@Override
	public void getContainer() {

	}

	/**
	 * 도커 컨테이너 목록을 조회.
	 */
	@Override
	public void getContainerList() {

	}

	/**
	 * 도커 컨테이너을 일시정지.
	 */
	@Override
	public void pauseContainer() {

	}
}
