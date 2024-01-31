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
@RequestMapping("/rocket/")
@RequiredArgsConstructor
public class FileController {

	private final FileManageService fileManageService;
	private final FileService fileService;

	@PostMapping(value = "/team/{teamCode}/projects/{projectCode}/files")
	public ResponseDto<RedisFileDto> uploadFile(
			@PathVariable String teamCode,
			@PathVariable String projectCode,
			@RequestPart("file") MultipartFile file
	) {
		RedisFileDto dto = fileManageService.store(file, teamCode, projectCode);
		return new ResponseDto<>(dto);
	}

	@GetMapping(value = "/team/{teamCode}/projects/{projectCode}/files/{fileHash}")
	public ResponseDto<List<String>> uploadFile(
			@PathVariable String fileHash,
			@PathVariable String teamCode,
			@PathVariable String projectCode
	) throws IOException {
		List<String> dto = fileService.readAllFilesInDirectory(teamCode, projectCode, fileHash);
		return new ResponseDto<>(dto);
	}


}
