package com.initcloud.rocket23.project.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.project.service.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping(value = "/file")
	public ResponseDto<?> uploadFile(
			@RequestPart("file") MultipartFile file
	) {
		fileService.store(file);
		return new ResponseDto<>(null);
	}

}
