package com.initcloud.dockerapi.container.middleware;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.github.dockerjava.api.command.CreateContainerResponse;

import com.github.dockerjava.api.exception.NotFoundException;
import com.initcloud.dockerapi.common.enums.ResponseCode;
import com.initcloud.dockerapi.common.exception.ApiException;
import com.initcloud.dockerapi.container.client.DockerContainerClient;
import com.initcloud.dockerapi.container.enums.DockerImages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerContainerApi implements ContainerApi {

	private static String CONTAINER_NAME_PREFIX = "rocket23_";
	private final DockerContainerClient dockerContainerClient = new DockerContainerClient();

	@Override
	public void create() {
		this.create(DockerImages.ALPINE_LATEST);
	}

	public void create(DockerImages image) {
		try {
			String containerName = CONTAINER_NAME_PREFIX + new SecureRandom().nextInt();
			this.pull(image);

			CreateContainerResponse container = dockerContainerClient.getDockerClient()
				.createContainerCmd(DockerImages.getFullImageName(image))
				.withCmd("env")
				.withName(containerName)
				.exec();

			log.info("Created container {}", container.toString());
		} catch (NullPointerException e) {
			throw new ApiException(e, ResponseCode.NULL_DOCKER_CLIENT);
		} catch (NotFoundException e) {
			log.info("Not Found Image {}", image);
			throw new ApiException(e, ResponseCode.DOCKER_IMAGE_NOT_FOUND);
		} catch (InterruptedException e) {
			log.warn("Couldn't pull Image {}", image);
			throw new ApiException(e, ResponseCode.DOCKER_CANNOT_PULL_IMAGE);
		}
	}

	@Override
	public void execute() {
		dockerContainerClient.getDockerClient()
			.pingCmd()
			.exec();
	}

	@Override
	public void inspect(String containerId) {
		dockerContainerClient.getDockerClient()
			.inspectContainerCmd(containerId)
			.exec();
	}

	@Override
	public void get() {
		dockerContainerClient.getDockerClient()
			.listContainersCmd()
			.exec();
	}

	@Override
	public void terminate(String containerId) {
		dockerContainerClient.getDockerClient()
			.stopContainerCmd(containerId)
			.exec();
	}

	public void pull(DockerImages image) throws InterruptedException {
		dockerContainerClient.getDockerClient()
			.pullImageCmd(image.getRepository())
			.withTag(image.getTag())
			.start()
			.awaitCompletion();
	}
}
