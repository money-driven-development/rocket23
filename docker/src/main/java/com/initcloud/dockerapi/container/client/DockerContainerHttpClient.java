package com.initcloud.dockerapi.container.client;

import java.net.URI;

import org.springframework.lang.Nullable;

import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerCmdExecFactory;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.transport.SSLConfig;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DockerContainerHttpClient {

	private final DockerClientProperties dockerClientProperties;
	private final DockerCmdExecFactory dockerCmdExecFactory;

	@Nullable
	public DockerHttpClient getHttpClient() {
		if (dockerCmdExecFactory instanceof DefaultDockerCmdExecFactory)
			return ((DefaultDockerCmdExecFactory)dockerCmdExecFactory).getDockerHttpClient();

		return null;
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

	public DockerHttpClient createDockerHttpClient() {
		return createDockerHttpClient(URI.create(dockerClientProperties.getDockerUnixHost()), null);
	}
}
