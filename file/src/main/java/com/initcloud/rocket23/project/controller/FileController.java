package com.initcloud.rocket23.project.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.project.dto.RedisFileDto;
import com.initcloud.rocket23.project.service.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/file/{teamCode}/{projectCode}")
	public ResponseDto<RedisFileDto> uploadFile(
			@PathVariable String teamCode,
			@PathVariable String projectCode,
			@RequestPart("file") MultipartFile file
	) {
		RedisFileDto dto = fileService.store(file, teamCode, projectCode);
		return new ResponseDto<>(dto);
	}

}
