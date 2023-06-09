package com.initcloud.dockerapi.container.middleware;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;

import com.github.dockerjava.api.exception.NotFoundException;
import com.initcloud.dockerapi.common.enums.ResponseCode;
import com.initcloud.dockerapi.common.exception.ApiException;
import com.initcloud.dockerapi.container.client.DockerContainerClient;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerContainerApi implements ContainerApi {

	private static String CONTAINER_NAME_PREFIX = "rocket23_";
	private static String IMAGE_ALPINE = "alpine";
	private final DockerContainerClient dockerContainerClient = new DockerContainerClient();

	@Override
	public void create() {
		create(IMAGE_ALPINE);
	}

	public void create(String image) {
		try {
			String containerName = CONTAINER_NAME_PREFIX + new SecureRandom().nextInt();
			pull();
			CreateContainerResponse container = dockerContainerClient.getDockerClient()
				.createContainerCmd(IMAGE_ALPINE)
				.withCmd("env")
				.withName(containerName)
				.exec();

			log.info("Created container {}", container.toString());
		} catch (NullPointerException e) {
			throw new ApiException(e, ResponseCode.DOCKER_IMAGE_NOT_FOUND);
		} catch (NotFoundException e) {
			log.info("Not Found Image {}", image);
			throw new ApiException(e, ResponseCode.DOCKER_IMAGE_NOT_FOUND);
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

	@Override
	public void pull() {
		try {
			pull(IMAGE_ALPINE, "latest");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pull(String image, String tag) throws InterruptedException {
		dockerContainerClient.getDockerClient().pullImageCmd(image)
			.withTag(tag)
			.start()
			.awaitCompletion();
	}
}
