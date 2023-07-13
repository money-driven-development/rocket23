package com.initcloud.rocket23.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;

@RestController
@RequestMapping("/user")
public class UserController {

	public ResponseDto<Object> profileDetails() {
		//Todo
		return new ResponseDto<>(null);
	}
}
