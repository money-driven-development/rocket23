package com.initcloud.dockerapi.container.middleware;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.initcloud.dockerapi.common.enums.ResponseCode;
import com.initcloud.dockerapi.common.exception.ApiException;
import com.initcloud.dockerapi.common.utils.DockerBufferStreamReader;
import com.initcloud.dockerapi.container.client.DockerClientProperties;
import com.initcloud.dockerapi.container.client.DockerContainerClient;
import com.initcloud.dockerapi.container.dto.IaCScanRequestDto;
import com.initcloud.dockerapi.container.enums.ContainerImages;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Service
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerContainerApi implements ContainerApi {

	private static String CONTAINER_NAME_PREFIX = "rocket23_";
	private final DockerClientProperties dockerClientProperties = new DockerClientProperties();
	private final DockerContainerClient dockerContainerClient = new DockerContainerClient();

	@Override
	public CreateContainerResponse create() {
		return this.create(ContainerImages.SCANNER_LATEST);
	}

	public CreateContainerResponse create(ContainerImages image) {
		try {
			CreateContainerResponse createContainerResponse = this.create(image, null);
			log.info("[CREATE] Container - {}", createContainerResponse.getId());
			return createContainerResponse;
		} catch (NullPointerException e) {
			throw new ApiException(e, ResponseCode.NULL_DOCKER_CLIENT);
		} catch (NotFoundException e) {
			log.info("[NOT FOUND] Image - {}", image);
			this.pull(image);
			return this.create(image, null);
		}
	}

	private CreateContainerResponse create(ContainerImages image, String env) {
		HostConfig hostConfig = setVolumeHostConfig();

		return dockerContainerClient.getDockerClient()
			.createContainerCmd(
				ContainerImages.getFullImageName(image)
			)
			.withTty(true)
			.withHostConfig(hostConfig)
			.withName(CONTAINER_NAME_PREFIX + new SecureRandom().nextInt())
			.exec();
	}

	@Override
	public String execute(String containerId, IaCScanRequestDto path) {
		try (PipedOutputStream outputStream = new PipedOutputStream()) {
			PipedInputStream inputStream = new PipedInputStream(outputStream);

			this.execStart(containerId, path.getIacPath(), outputStream);
			this.getLogFromContainer(containerId, outputStream);

			return DockerBufferStreamReader.outputStreamToStringBuilder(inputStream).toString();
		} catch (IOException e) {
			throw new ApiException(ResponseCode.DOCKER_CANNOT_READ_SCAN_OUTPUT);
		} catch (InterruptedException e) {
			throw new ApiException(ResponseCode.SERVER_ERROR);
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

	/**
	 *	Docker 명령을 exec 로 실행하고
	 *	그 출력(표준 출력, 표준 에러)을 DockerCmdResultCallback 으로 반환.
	 */
	public DockerCmdResultCallback execStart(String containerId, String dir, PipedOutputStream outputStream) throws
		IOException, InterruptedException {

		String execId = this.execCreate(containerId, dir);

		return dockerContainerClient.getDockerClient()
			.execStartCmd(execId)
			// Todo - .withStdin() 처리가 필요한지 확인
			.exec(new DockerCmdResultCallback(outputStream));
	}

	/**
	 *	컨테이너의 CMD 실행을 위한 EXEC Id 생성
	 */
	private String execCreate(String containerId, String dir) {
		return dockerContainerClient.getDockerClient()
			.execCreateCmd(containerId)
			.withAttachStdout(true)
			.withAttachStderr(true)
			.withCmd("checkov", "-o", "json", "-d", dockerClientProperties.getVolumeContainerRoot() + dir)
			.exec()
			.getId();
	}

	/**
	 *	(Running 상태가 아닌) 컨테이너를 실행
	 */
	public Void start(String containerId) {
		return dockerContainerClient.getDockerClient()
			.startContainerCmd(containerId)
			.exec();
	}

	/**
	 *	컨테이너를 삭제
	 */
	public Void remove(String containerId) {
		try {
			return dockerContainerClient.getDockerClient()
				.removeContainerCmd(containerId)
				.withRemoveVolumes(true)
				.withForce(true)
				.exec();
		} catch (NotFoundException e) {
			log.warn("[NOT FOUND] Container - {}", containerId);
			return null;
		}
	}

	/**
	 *	컨테이너 이미지를 다운로드
	 */
	public void pull(ContainerImages image) {
		try {
			dockerContainerClient.getDockerClient()
				.pullImageCmd(image.getRepository())
				.withTag(image.getTag())
				.start()
				.awaitCompletion();
		} catch (InterruptedException e) {
			log.warn("[IMAGE PULL] Couldn't pull Image - {}", image);
			throw new ApiException(e, ResponseCode.DOCKER_CANNOT_PULL_IMAGE);
		}
	}

	/**
	 * 컨테이너의 표준 출력, 표춘 에러를 가져옴.
	 */
	public DockerCmdResultCallback getLogFromContainer(String containerId, PipedOutputStream outputStream) throws
		IOException {
		return dockerContainerClient.getDockerClient()
			.logContainerCmd(containerId)
			.withStdOut(true)
			.withStdErr(true)
			.withFollowStream(true)
			.withTailAll()
			.exec(new DockerCmdResultCallback(outputStream));
	}

	public HostConfig setVolumeHostConfig() {
		return HostConfig.newHostConfig()
			.withBinds(
				new Bind(
					"/home/ubuntu/nas/uploads/",
					new Volume(dockerClientProperties.getVolumeContainerRoot()))
			);
	}
}
