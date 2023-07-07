package com.initcloud.dockerapi.container.dto;

import java.io.Serializable;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;

import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerDto implements Serializable {

	private String containerId;
	private String containerStatus;

	public ContainerDto(String containerId, String containerStatus) {
		this.containerStatus = containerStatus;
		this.containerId = containerId;
	}

	public ContainerDto(final Container container) throws NullPointerException {
		this.containerId = container.getId();
		this.containerStatus = container.getState();
	}

	public ContainerDto(final CreateContainerResponse container) throws NullPointerException {
		this.containerId = container.getId();
		this.containerStatus = "Created";
	}
}
