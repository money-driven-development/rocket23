package com.initcloud.dockerapi.container.controller;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.dockerapi.common.dto.ResponseDto;
import com.initcloud.dockerapi.container.dto.DockerDto;
import com.initcloud.dockerapi.container.service.DockerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/container")
@RequiredArgsConstructor
public class DockerContainerController {

	private final DockerService dockerService;

	@GetMapping
	public ResponseDto containerList() {

		dockerService.getContainerList();

		return new ResponseDto<>(null);
	}

	@GetMapping("/{containerId}")
	public ResponseDto<ContainerDto> containerDetails(@Nullable @PathVariable String containerId) {

		ContainerDto response = dockerService.getContainerDetails(containerId);

		return new ResponseDto<>(response);
	}

	@PostMapping
	public ResponseDto containerRun() {

		dockerService.executeContainer(1);

		return new ResponseDto<>(null);
	}
}
