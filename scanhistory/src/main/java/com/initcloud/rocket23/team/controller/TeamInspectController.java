package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.team.dto.TeamMemberDto;
import com.initcloud.rocket23.team.service.TeamInspectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;


@ApiOperation("Team Inspect API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamInspectController {

    private final TeamInspectService teamInspectService;

    @ApiOperation(value = "Get Member List with Page", notes = "Retrieve paged list of members.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageable", paramType = "query", value = "paging value", required = true, dataTypeClass = Pageable.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/members")
    public ResponseDto<Page<TeamMemberDto.Summary>> memberList(
            final Pageable pageable,
            @PathVariable String teamCode
    ) {
        Page<TeamMemberDto.Summary> response = teamInspectService.getTeamMemberList(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Get Member details", notes = "Retrieve specific member details", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "memberEmail", paramType = "path", value = "member email", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/members/{memberEmail}")
    public ResponseDto<Object> memberDetails(
            @PathVariable String teamCode,
            @PathVariable String memberEmail // Todo - 추후 고유 유저명으로 변경할 예정
    ) {
        TeamMemberDto.Details response = teamInspectService.getTeamMemberDetails(teamCode, memberEmail);

        return new ResponseDto<>(response);
    }
}
