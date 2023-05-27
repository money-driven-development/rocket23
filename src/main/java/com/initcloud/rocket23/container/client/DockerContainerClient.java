package com.initcloud.rocket23.container.client;

import java.time.Duration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerContainerClient {

	private DockerClientConfig dockerClientConfig;
	private DockerHttpClient dockerHttpClient;

	public DockerContainerClient(DockerClientConfig dockerClientConfig, DockerHttpClient dockerHttpClient) {
		this.dockerClientConfig = dockerClientConfig;
		this.dockerHttpClient = dockerHttpClient;
	}

	public DockerClient getDockerClient() {
		return DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
	}

	/**
	 * 의존성 - Apache HttpClient library version 5
	 */
	public DockerHttpClient getDockerHttpClient() {
		return new ApacheDockerHttpClient.Builder()
			.dockerHost(dockerClientConfig.getDockerHost())
			.sslConfig(dockerClientConfig.getSSLConfig())
			.maxConnections(100)
			.connectionTimeout(Duration.ofSeconds(30))
			.responseTimeout(Duration.ofSeconds(45))
			.build();
	}

}
