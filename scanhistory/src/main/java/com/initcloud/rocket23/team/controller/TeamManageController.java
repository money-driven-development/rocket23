package com.initcloud.rocket23.team.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.service.TeamManageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamManageController {

	private final TeamManageService teamManageService;

	// ===============================
	// ========== 팀 멤버 관리 ==========
	// ===============================

	/**
	 * 사용자 팀 초대
	 * @return
	 */
	@PostMapping("/member")
	public ResponseDto<String> memberInvite(@RequestBody TeamInviteDto.Request request) {
		String response = teamManageService.inviteUser(request);

		return new ResponseDto<>(response);
	}

	/**
	 * 멤버 사용자 상태 변경
	 * @return
	 */
	@PutMapping("/member")
	public ResponseDto<Object> memberStatus() {
		//Todo
		return new ResponseDto<>(null);
	}

	/**
	 * 멤버 사용자 제거
	 * @return
	 */
	@DeleteMapping("/member")
	public ResponseDto<Object> memberRemove() {
		//Todo
		return new ResponseDto<>(null);
	}

	// ===============================
	// ========== 팀 정보 관리 ==========
	// ===============================

	/**
	 * 팀 해체
	 * @return
	 */
	@DeleteMapping
	public ResponseDto<Object> teamRemove() {
		//Todo
		return new ResponseDto<>(null);
	}
}
