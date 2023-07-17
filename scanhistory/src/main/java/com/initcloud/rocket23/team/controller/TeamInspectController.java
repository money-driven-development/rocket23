package com.initcloud.rocket23.team.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;

@RestController
@RequestMapping("/team")
public class TeamInspectController {

	@GetMapping
	public ResponseDto<Object> memberList() {
		//Todo
		return new ResponseDto<>(null);
	}

	@GetMapping("/{memberName}")
	public ResponseDto<Object> memberDetails(@PathVariable String memberName) {
		//Todo
		return new ResponseDto<>(null);
	}
}
