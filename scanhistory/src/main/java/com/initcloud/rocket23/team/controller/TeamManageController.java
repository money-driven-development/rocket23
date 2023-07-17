package com.initcloud.rocket23.team.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;

@RestController
@RequestMapping("/team")
public class TeamManageController {

	@PutMapping("/status")
	public ResponseDto<Object> memberStatus() {
		//Todo
		return new ResponseDto<>(null);
	}

	@DeleteMapping
	public ResponseDto<Object> teamRemove() {
		//Todo
		return new ResponseDto<>(null);
	}
}
