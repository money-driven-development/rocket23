package com.initcloud.rocket23.team.controller;

import org.springframework.web.bind.annotation.*;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.service.TeamManageService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

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
     *
     * @return
     */
    @PostMapping("/{teamCode}/members")
    public ResponseDto<String> memberInvite(
            @PathVariable String teamCode,
            @RequestBody @Valid TeamInviteDto.Request request
    ) {
        String response = teamManageService.inviteUser(teamCode, request);

        return new ResponseDto<>(response);
    }

    /**
     * 멤버 사용자 상태 변경
     *
     * @return
     */
    @PutMapping("/{teamCode}/members/{memberEmail}")
    public ResponseDto<Object> memberStatus(
            @PathVariable String teamCode,
            @PathVariable String memberEmail
    ) {
        //Todo
        return new ResponseDto<>(null);
    }

    /**
     * 멤버 사용자 제거
     *
     * @return
     */
    @DeleteMapping("/{teamCode}/members/{memberEmail}")
    public ResponseDto<Object> memberRemove(
            @PathVariable String teamCode,
            @PathVariable String memberEmail
    ) {
        boolean response = teamManageService.removeMemberFromTeam(teamCode, memberEmail);

        return new ResponseDto<>(response);
    }

    // ===============================
    // ========== 팀 정보 관리 ==========
    // ===============================

    /**
     * 팀 해체
     *
     * @return
     */
    @DeleteMapping("/{teamCode}")
    public ResponseDto<Object> teamRemove(
            @PathVariable String teamCode
    ) {
        boolean response = teamManageService.removeTeam(teamCode);

        return new ResponseDto<>(response);
    }
}
