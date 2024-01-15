package com.initcloud.rocket23.project.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.project.dto.RedisFileDto;
import com.initcloud.rocket23.project.service.FileManageService;

import com.initcloud.rocket23.project.service.FileService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class FileController {

	private final FileManageService fileManageService;
	private final FileService fileService;

	@PostMapping(value = "/file/{teamCode}/{projectCode}")
	public ResponseDto<RedisFileDto> uploadFile(
			@PathVariable String teamCode,
			@PathVariable String projectCode,
			@RequestPart("file") MultipartFile file
	) {
		RedisFileDto dto = fileManageService.store(file, teamCode, projectCode);
		return new ResponseDto<>(dto);
	}

	@GetMapping(value = "/{fileHash}")
	public ResponseDto<List<String>> uploadFile(
			@PathVariable String fileHash
	) throws IOException {
		List<String> dto = fileService.readAllFilesInDirectory(fileHash);
		return new ResponseDto<>(dto);
	}

}
