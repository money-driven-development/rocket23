package com.initcloud.dockerapi.container.middleware;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.dockerjava.api.command.CreateContainerResponse;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import com.initcloud.dockerapi.common.enums.ResponseCode;
import com.initcloud.dockerapi.common.exception.ApiException;
import com.initcloud.dockerapi.container.client.DockerContainerClient;
import com.initcloud.dockerapi.container.enums.ContainerImages;

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
	public CreateContainerResponse create() {
		return this.create(ContainerImages.ALPINE_LATEST);
	}

	public CreateContainerResponse create(ContainerImages image) {
		try {
			String containerName = CONTAINER_NAME_PREFIX + new SecureRandom().nextInt();
			this.pull(image);

			log.info("Created container");
			return dockerContainerClient.getDockerClient()
				.createContainerCmd(ContainerImages.getFullImageName(image))
				.withCmd("env")
				.withName(containerName)
				.exec();

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
	public Void execute() {
		return dockerContainerClient.getDockerClient()
			.pingCmd()
			.exec();
	}

	@Override
	public InspectContainerResponse inspect(String containerId) {
		return dockerContainerClient.getDockerClient()
			.inspectContainerCmd(containerId)
			.exec();
	}

	@Override
	public List<Container> get() {
		return dockerContainerClient.getDockerClient()
			.listContainersCmd()
			.exec();
	}

	@Override
	public Void stop(String containerId) {
		return dockerContainerClient.getDockerClient()
			.stopContainerCmd(containerId)
			.exec();
	}

	@Override
	public Void terminate(String containerId) {
		return dockerContainerClient.getDockerClient()
			.stopContainerCmd(containerId)
			.exec();
	}

	public Void remove(String containerId) {
		return dockerContainerClient.getDockerClient()
			.removeContainerCmd(containerId)
			.withRemoveVolumes(true)
			.withForce(true)
			.exec();
	}

	public void pull(ContainerImages image) throws InterruptedException {
		dockerContainerClient.getDockerClient()
			.pullImageCmd(image.getRepository())
			.withTag(image.getTag())
			.start()
			.awaitCompletion();
	}
}
