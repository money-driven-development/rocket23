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
    @PostMapping("/{teamCode}/member")
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
    @PutMapping("/member")
    public ResponseDto<Object> memberStatus() {
        //Todo
        return new ResponseDto<>(null);
    }

    /**
     * 멤버 사용자 제거
     *
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
     *
     * @return
     */
    @DeleteMapping
    public ResponseDto<Object> teamRemove() {
        //Todo
        return new ResponseDto<>(null);
    }
}
