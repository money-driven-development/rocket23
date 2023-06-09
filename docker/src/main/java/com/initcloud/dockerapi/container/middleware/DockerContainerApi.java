package com.initcloud.dockerapi.container.middleware;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

import com.initcloud.dockerapi.container.client.DockerContainerClient;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerContainerApi implements ContainerApi {

	private static String CONTAINER_NAME_PREFIX = "rocket23_";
	private static String IMAGE_ALPINE = "alpine:3.18.0";
	private DockerContainerClient dockerContainerClient;

	public DockerContainerApi(DockerContainerClient dockerContainerClient) {
		this.dockerContainerClient = dockerContainerClient;
	}

	@Override
	public void create() {
		try {
			String containerName = CONTAINER_NAME_PREFIX + new SecureRandom().nextInt();

			CreateContainerResponse container = dockerContainerClient.getDockerClient()
				.createContainerCmd(IMAGE_ALPINE)
				.withCmd("env")
				.withName(containerName)
				.exec();

			log.info("Created container {}", container.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {
		DockerClient dockerClient = dockerContainerClient.getDockerClient();
		dockerClient.pingCmd().exec();
	}

	@Override
	public void get() {

	}

	@Override
	public void terminate() {

	}
}
