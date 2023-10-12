package com.initcloud.dockerapi.container.enums;

import lombok.Getter;

public enum ContainerAPIType {
	/**
	 * 오케스트레이션 여부와 상관 없이 도커 API 를 사용할 경우
	 * 도커 소켓은 컨테이너 내부가 아닌 호스트의 소켓으로 연결되어야 함.
	 * 즉, 도커 소켓에 대한 바인드(볼륨)가 필수.
	 */
	DOCKER("unix:///var/run/docker.sock"),

	/**
	 * 오케스트레이션 여부와 상관 없이 Containerd API 를 사용할 경우.
	 * 도커와 마찬가지로 호스트에 존재하는 소켓에 대한 바인드가 필요함.
	 */
	GRPC_CONTAINERD(""),

	/**
	 * 쿠버네티스 API를 사용할 경우
	 * 쿠버네티스 API는 호스트 상에서 접근할 수 있음.
	 * 이에 대한 접근 방안을 고려해야 함.
	 */
	KUBERNETES("");

	@Getter
	private String socket;

	ContainerAPIType(String socket) {
		this.socket = socket;
	}
}
