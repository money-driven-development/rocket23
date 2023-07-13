package com.initcloud.rocket23.team.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;

@RestController
@RequestMapping("/team/project")
public class TeamProjectController {

	public ResponseDto<Object> projectList() {
		//Todo
		return new ResponseDto<>(null);
	}

	public ResponseDto<Object> projectDetails() {
		//Todo
		return new ResponseDto<>(null);
	}

	public ResponseDto<Object> projectAdd() {
		//Todo
		return new ResponseDto<>(null);
	}
}
