package com.initcloud.dockerapi.container.client;

import java.net.URI;

import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.transport.SSLConfig;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerContainerHttpClient {

	/**
	 * 의존성 - Apache HttpClient library version 5
	 */
	public static DockerHttpClient createDockerHttpClient(URI dockerHost, SSLConfig sslConfig) {
		return new ApacheDockerHttpClient.Builder()
			.dockerHost(dockerHost)
			.sslConfig(sslConfig)
			.build();
	}

	public static DockerHttpClient createDockerHttpClient() {
		return new ApacheDockerHttpClient.Builder()
			.dockerHost(URI.create("unix:///var/run/docker.sock"))
			.sslConfig(null)
			.build();
	}
}
