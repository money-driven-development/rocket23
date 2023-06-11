package com.initcloud.dockerapi.container.dto;

import java.time.LocalDateTime;

import com.initcloud.dockerapi.container.enums.ContainerAPIType;
import com.initcloud.dockerapi.container.enums.ContainerStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerDto {

	private Long pid;
	private Long port;
	private String containerId;
	private LocalDateTime startTime;
	private ContainerAPIType apiType;
	private ContainerStatus containerStatus;

	@Builder(builderClassName = "containerBuilder", builderMethodName = "containerStatusBuilder")
	public ContainerDto(Long pid, Long port, String containerId, LocalDateTime startTime, ContainerAPIType apiType,
		ContainerStatus containerStatus) {
		this.pid = pid;
		this.port = port;
		this.containerId = containerId;
		this.startTime = startTime;
		this.apiType = apiType;
		this.containerStatus = containerStatus;
	}
}
