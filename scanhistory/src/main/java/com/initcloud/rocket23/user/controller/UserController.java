package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.user.dto.UserDto;
import com.initcloud.rocket23.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiOperation("User Info API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/user")
public class UserController {

	private final UserService userService;

	@ApiOperation(value = "User Profile", notes = "Show user's individual profile.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Authorization Code", required = true, dataTypeClass = UserDto.Profile.class)})
	@GetMapping()
	public ResponseDto<UserDto.Profile> profileDetails() {
		UserDto.Profile response = userService.getUserDetails();

		return new ResponseDto<>(response);
	}

	// 내가 속한 팀 목록
	@ApiOperation(value = "User's Team List", notes = "Show user's teams.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Authorization Code", required = true, dataTypeClass = List.class)})
	@GetMapping("teams")
	public ResponseDto<List<UserDto.MyTeam>> userTeams() {
		List<UserDto.MyTeam> response = userService.getUserTeamList();

		return new ResponseDto<>(response);
	}
}
