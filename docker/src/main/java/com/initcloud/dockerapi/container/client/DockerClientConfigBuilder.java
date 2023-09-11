package com.initcloud.dockerapi.container.client;

import java.net.URI;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.transport.SSLConfig;
import com.initcloud.dockerapi.container.dto.DockerDto;
import com.initcloud.dockerapi.container.enums.ContainerAPIType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerClientConfigBuilder {

	/**
	 * 기본 도커 클라이언트 구성을 반환합니다.
	 * @return DockerClientConfig
	 */
	public static DockerClientConfig buildDefaultDockerClientConfig() {
		return DefaultDockerClientConfig.createDefaultConfigBuilder()
			.withDockerHost(ContainerAPIType.DOCKER.getSocket())
			.build();
	}

	/**
	 * 도커 레지스트리가 설정된 클라이언트 구성을 반환합니다.
	 * @return DockerClientConfig
	 */
	public static DockerClientConfig buildDockerClientConfigWithRegistry(DockerDto.Registry registry) throws NullPointerException {
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

	/**
	 * 의존성 - Apache HttpClient library version 5
	 */
	public DockerHttpClient createDockerHttpClient(URI dockerHost, SSLConfig sslConfig) {
		return new ApacheDockerHttpClient.Builder()
			.dockerHost(dockerHost)
			.sslConfig(sslConfig)
			.build();
	}
}
