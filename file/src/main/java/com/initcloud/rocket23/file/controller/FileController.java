package com.initcloud.rocket23.file.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.file.service.FileService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@ApiOperation(value = "Get Repository List", notes = "Get Repository List from Github.", response = ResponseDto.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
			@ApiImplicitParam(name = "file", paramType = "body", value = "It is Multipart, Not JSON", required = true, dataTypeClass = MultipartFile.class)})
	@PostMapping(value = "/file")
	public ResponseDto<?> uploadFile(@RequestPart("file") MultipartFile file) {
		fileService.store(file);
		return new ResponseDto<>(null);
	}

}
