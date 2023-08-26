package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.team.dto.TeamDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.service.TeamManageService;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@ApiOperation("Team Manage API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/team")
public class TeamManageController {

    private final TeamManageService teamManageService;

    // ===============================
    // ========== 팀 멤버 관리 ==========
    // ===============================

    /**
     * 사용자 팀 초대
     */
    @ApiOperation(value = "Invite member", notes = "Invite a member to team.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "request", paramType = "body", value = "Request info", required = true, dataTypeClass = TeamInviteDto.Request.class)})
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
     */
    @ApiOperation(value = "Modify member details", notes = "Modify a member details", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "memberEmail", paramType = "path", value = "member email", required = true, dataTypeClass = String.class)})
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
     */
    @ApiOperation(value = "Remove team member", notes = "Remove a member from team.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "memberEmail", paramType = "path", value = "member email", required = true, dataTypeClass = String.class)})
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
     * 팀 생성
     */
    @ApiOperation(value = "Remove team", notes = "Remove a team.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "request", paramType = "body", value = "Team Create Dto", required = true, dataTypeClass = TeamDto.class)})
    @PostMapping("/")
    public ResponseDto<String> teamRemove(
            @RequestBody TeamDto request
    ) {
        String response = teamManageService.addTeam(request);

        return new ResponseDto<>(response);
    }

    /**
     * 팀 해체
     */
    @ApiOperation(value = "Remove team", notes = "Remove a team.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @DeleteMapping("/{teamCode}")
    public ResponseDto<Object> teamRemove(
            @PathVariable String teamCode
    ) {
        boolean response = teamManageService.removeTeam(teamCode);

        return new ResponseDto<>(response);
    }
}
