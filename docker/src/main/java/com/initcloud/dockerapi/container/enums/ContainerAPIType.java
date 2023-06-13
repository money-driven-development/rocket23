package com.initcloud.dockerapi.container.enums;

public enum ContainerAPIType {
	/**
	 * 오케스트레이션 여부와 상관 없이 도커 API 를 사용할 경우
	 */
	DOCKER,

	/**
	 * 오케스트레이션 여부와 상관 없이 Containerd API 를 사용할 경우
	 */
	GRPC_CONTAINERD,

	/**
	 * 쿠버네티스 API를 사용할 경우
	 */
	KUBERNETES
}
