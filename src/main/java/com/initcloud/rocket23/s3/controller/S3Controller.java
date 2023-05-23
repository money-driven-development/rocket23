package com.initcloud.rocket23.s3.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.s3.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rocket23/")
@RequiredArgsConstructor
public class S3Controller {

	private final S3Service s3Service;

	@PostMapping(value = "/s3")
	public ResponseDto<?> uploadFiletoS3(@RequestPart("file") MultipartFile file) {
		s3Service.store(file);
		return new ResponseDto<>(null);
	}
}
