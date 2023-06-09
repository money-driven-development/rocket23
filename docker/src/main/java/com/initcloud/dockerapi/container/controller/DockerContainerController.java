package com.initcloud.dockerapi.container.controller;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.dockerapi.common.dto.ResponseDto;
import com.initcloud.dockerapi.container.service.DockerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/container")
@RequiredArgsConstructor
public class DockerContainerController {

	private final DockerService dockerService;

	@GetMapping
	public ResponseDto containerDetails(@Nullable @RequestParam String containerId) {

		if (containerId == null)
			dockerService.getContainerList();
		else
			dockerService.getContainer(containerId);

		return new ResponseDto<>(null);
	}

	@PostMapping
	public ResponseDto containerRun() {

		dockerService.executeContainer(1);

		return new ResponseDto<>(null);
	}
}
