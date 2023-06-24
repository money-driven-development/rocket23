package com.initcloud.dockerapi.container.dto;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.initcloud.dockerapi.container.enums.ContainerAPIType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerInspectDto extends ContainerDto {
	private Long pid;
	private String containerName;
	private String startAt;
	private String image;
	private ContainerAPIType apiType;
	private String[] args;

	@Builder(builderClassName = "containerBuilder", builderMethodName = "containerDetailsStatusBuilder")
	public ContainerInspectDto(Long pid, String containerId, String containerName, String[] args, String image, String startAt,
		ContainerAPIType apiType, String containerStatus) {
		super(containerId, containerStatus);
		this.pid = pid;
		this.containerName = containerName;
		this.startAt = startAt;
		this.image = image;
		this.apiType = apiType;
		this.args = args;
	}

	public ContainerInspectDto(final InspectContainerResponse container, final ContainerAPIType apiType) throws
		NullPointerException {
		super(container.getId(), container.getState().getStatus());
		this.pid = container.getState().getPidLong();
		this.containerName = container.getName();
		this.startAt = container.getState().getStartedAt();
		this.image = container.getImageId();
		this.apiType = apiType;
		this.args = container.getArgs();
	}
}
