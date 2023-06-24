package com.initcloud.dockerapi.container.controller;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.dockerapi.common.dto.ResponseDto;
import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.dto.ContainerInspectDto;
import com.initcloud.dockerapi.container.service.DockerInspectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/containers")
@RequiredArgsConstructor
public class DockerContainerInspectController {

	private final DockerInspectService dockerService;

	/**
	 * 컨테이너 목록 조회
	 * @return List<ContainerDto>
	 */
	@GetMapping
	public ResponseDto<List<ContainerDto>> containerList() {

		List<ContainerDto> response = dockerService.getContainerList();

		return new ResponseDto<>(response);
	}

	/**
	 * 컨테이너 메타데이터 상세 조회
	 * @return ContainerInspectDto
	 */
	@GetMapping("/{containerId}")
	public ResponseDto<ContainerInspectDto> containerDetails(@Nullable @PathVariable String containerId) {

		ContainerInspectDto response = dockerService.getContainerDetails(containerId);

		return new ResponseDto<>(response);
	}

	/** Todo - 실시간 업데이트 처리 예정
	 * 컨테이너 로그 조회
	 * @return ContainerInspectDto
	 */
	@GetMapping("/{containerId}/log")
	public ResponseDto<ContainerDto> containerLogs(@Nullable @PathVariable String containerId) {

		ContainerDto response = dockerService.getContainerDetails(containerId);

		return new ResponseDto<>(response);
	}

	/**
	 * 컨테이너 자원 상태 상세 조회
	 * @return ContainerInspectDto
	 */
	@GetMapping("/{containerId}/metrics")
	public ResponseDto<ContainerDto> containerMetrics(@Nullable @PathVariable String containerId) {

		ContainerDto response = dockerService.getContainerDetails(containerId);

		return new ResponseDto<>(response);
	}
}
