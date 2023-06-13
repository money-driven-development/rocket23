package com.initcloud.dockerapi.container.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.dockerapi.common.dto.ResponseDto;

import com.initcloud.dockerapi.container.service.DockerManageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/container")
@RequiredArgsConstructor
public class DockerContainerManageController {

	private final DockerManageService dockerService;

	@PostMapping
	public ResponseDto containerRun() {

		dockerService.executeContainer(1);

		return new ResponseDto<>(null);
	}
}
