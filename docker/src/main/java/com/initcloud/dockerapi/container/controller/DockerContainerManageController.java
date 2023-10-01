package com.initcloud.dockerapi.container.controller;

import com.initcloud.dockerapi.common.dto.ResponseDto;
import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.dto.IaCScanRequestDto;
import com.initcloud.dockerapi.container.service.DockerManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rocket/containers")
@RequiredArgsConstructor
public class DockerContainerManageController {

	private final DockerManageService dockerService;

//	@ApiOperation(value = "Scan IaC", notes = "Scan IaC", response = ResponseDto.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
//			@ApiImplicitParam(name = "request", paramType = "body", value = "Scan request", required = true, dataTypeClass = IaCScanRequestDto.class)})
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
