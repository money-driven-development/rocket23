package com.initcloud.dockerapi.container.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.dockerapi.common.dto.ResponseDto;

import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.dto.IaCScanRequestDto;
import com.initcloud.dockerapi.container.service.DockerManageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/containers")
@RequiredArgsConstructor
public class DockerContainerManageController {

	private final DockerManageService dockerService;

	@ApiOperation(value = "Scan IaC", notes = "Scan IaC", response = ResponseDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
			@ApiImplicitParam(name = "request", paramType = "body", value = "Scan request", required = true, dataTypeClass = IaCScanRequestDto.class)})
	@PostMapping
	public ResponseDto containerRun(@RequestBody IaCScanRequestDto request) {

		ContainerDto response = dockerService.executeContainer(1, request);

		return new ResponseDto<>(response);
	}

	@DeleteMapping("/{containerId}")
	public ResponseDto containerTerminate(@PathVariable String containerId) {

		Boolean response = dockerService.terminateContainer(containerId);

		return new ResponseDto<>(response);
	}
}
