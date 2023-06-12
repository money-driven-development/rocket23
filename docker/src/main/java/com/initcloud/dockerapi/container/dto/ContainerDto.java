package com.initcloud.dockerapi.container.dto;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.initcloud.dockerapi.container.enums.ContainerAPIType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerDto {

	private Long pid;
	private String containerStatus;
	private String startAt;
	private String containerId;
	private String image;
	private ContainerAPIType apiType;
	private String[] args;

	@Builder(builderClassName = "containerBuilder", builderMethodName = "containerStatusBuilder")
	public ContainerDto(Long pid, String containerId, String[] args, String image, String startAt,
		ContainerAPIType apiType, String containerStatus) {
		this.pid = pid;
		this.containerId = containerId;
		this.args = args;
		this.image = image;
		this.startAt = startAt;
		this.apiType = apiType;
		this.containerStatus = containerStatus;
	}

	public ContainerDto(final InspectContainerResponse container, final ContainerAPIType apiType) throws
		NullPointerException {
		this.pid = container.getState().getPidLong();
		this.startAt = container.getState().getStartedAt();
		this.containerStatus = container.getState().getStatus();
		this.containerId = container.getName();
		this.image = container.getImageId();
		this.apiType = apiType;
		this.args = container.getArgs();
	}
}
