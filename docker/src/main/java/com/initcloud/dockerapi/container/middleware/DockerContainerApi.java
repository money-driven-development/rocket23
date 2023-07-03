package com.initcloud.dockerapi.container.middleware;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import com.initcloud.dockerapi.common.enums.ResponseCode;
import com.initcloud.dockerapi.common.exception.ApiException;
import com.initcloud.dockerapi.common.utils.DockerBufferStreamReader;
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
		return this.create(ContainerImages.SCANNER_ALPINE_LATEST);
	}

	public CreateContainerResponse create(ContainerImages image) {
		try {
			CreateContainerResponse createContainerResponse = this.create(image, null);
			log.info("Created container - {}", createContainerResponse.getId());
			return createContainerResponse;
		} catch (NullPointerException e) {
			throw new ApiException(e, ResponseCode.NULL_DOCKER_CLIENT);
		} catch (NotFoundException e) {
			log.info("Not Found Image {}", image);
			this.pull(image);
			return this.create(image, null);
		}
	}

	private CreateContainerResponse create(ContainerImages image, String cmd) {
		return dockerContainerClient.getDockerClient()
			.createContainerCmd(ContainerImages.getFullImageName(image))
			.withCmd(cmd)
			.withName(CONTAINER_NAME_PREFIX + new SecureRandom().nextInt())
			.exec();
	}

	@Override
	public String execute(String containerId) {
		DockerClient dockerClient = dockerContainerClient.getDockerClient();

		try (PipedOutputStream outputStream = new PipedOutputStream()) {
			PipedInputStream inputStream = new PipedInputStream(outputStream);

			dockerClient.startContainerCmd(containerId).exec();
			getLogFromContainer(containerId, dockerClient, outputStream);
			StringBuilder scanOutput = DockerBufferStreamReader.outputStreamToStringBuilder(inputStream);

			return scanOutput.toString();
		} catch (IOException e) {
			throw new ApiException(ResponseCode.DOCKER_CANNOT_READ_SCAN_OUTPUT);
		}
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
		try {
			return dockerContainerClient.getDockerClient()
				.removeContainerCmd(containerId)
				.withRemoveVolumes(true)
				.withForce(true)
				.exec();
		} catch (NotFoundException e) {
			log.warn("Not Found Container {}", containerId);
			return null;
		}
	}

	public void pull(ContainerImages image) {
		try {
			dockerContainerClient.getDockerClient()
				.pullImageCmd(image.getRepository())
				.withTag(image.getTag())
				.start()
				.awaitCompletion();
		} catch (InterruptedException e) {
			log.warn("Couldn't pull Image {}", image);
			throw new ApiException(e, ResponseCode.DOCKER_CANNOT_PULL_IMAGE);
		}
	}

	public DockerCmdResultCallback getLogFromContainer(String containerId, DockerClient dockerClient,
		PipedOutputStream outputStream) throws IOException {
		return dockerClient.logContainerCmd(containerId)
			.withStdOut(true)
			.withStdErr(true)
			.withFollowStream(true)
			.withTailAll()
			.exec(new DockerCmdResultCallback(outputStream));
	}
}



