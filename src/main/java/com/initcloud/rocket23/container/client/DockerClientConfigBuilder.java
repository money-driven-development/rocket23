package com.initcloud.rocket23.container.client;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.initcloud.rocket23.container.dto.DockerDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerClientConfigBuilder {

	/**
	 * 기본 도커 클라이언트 구성을 반환합니다.
	 * @return DockerClientConfig
	 */
	public static DockerClientConfig buildDefaultDockerClientConfig() {
		return DefaultDockerClientConfig.createDefaultConfigBuilder().build();
	}

	/**
	 * 도커 레지스트리가 설정된 클라이언트 구성을 반환합니다.
	 * @return DockerClientConfig
	 */
	public static DockerClientConfig buildDockerRegistry(DockerDto.Registry registry) throws NullPointerException {
		DockerDto.User dockerUser = registry.getRegistryUser();

		return DefaultDockerClientConfig.createDefaultConfigBuilder()
			.withDockerHost(registry.getDockerHost())
			.withDockerTlsVerify(registry.isDockerTlsVerify())
			.withDockerCertPath(registry.getDockerCertPath())
			.withRegistryUrl(registry.getRegistryUrl())
			.withRegistryUsername(dockerUser.getRegistryUsername())
			.withRegistryPassword(dockerUser.getRegistryPassword())
			.withRegistryEmail(dockerUser.getRegistryEmail())
			.build();
	}
}
