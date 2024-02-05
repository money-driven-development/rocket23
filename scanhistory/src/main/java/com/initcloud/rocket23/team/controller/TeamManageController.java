package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.TeamDto;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.service.TeamManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Team Manage API")
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
    @Operation(
            summary = "Invite member",
            description = "Invite a member to team.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TeamInviteDto.Request.class)), description = "Request info", required = true)
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
    @Operation(
            summary = "Modify member details",
            description = "Modify a member details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "memberEmail", in = ParameterIn.PATH, description = "member email", required = true, schema = @Schema(type = "string"))
    @PatchMapping("/{teamCode}/members/{memberEmail}")
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
    @Operation(
            summary = "Remove team member",
            description = "Remove a member from team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "memberEmail", in = ParameterIn.PATH, description = "member email", required = true, schema = @Schema(type = "string"))
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
    @Operation(
            summary = "Create team",
            description = "Create a team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TeamDto.class)), description = "Team Create Dto", required = true)
    @PostMapping
    public ResponseDto<String> teamCreate(
            @RequestBody TeamDto.Team request
    ) {
        String response = teamManageService.addTeam(request);

       return new ResponseDto<>(response);
    }

    /**
     * 팀 해체
     */
    @Operation(
            summary = "Remove team",
            description = "Remove a team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @DeleteMapping("/{teamCode}")
    public ResponseDto<Object> teamRemove(
            @PathVariable String teamCode
    ) {
        boolean response = teamManageService.removeTeam(teamCode);

        return new ResponseDto<>(response);
    }

    /**
     * 팀 정보 조회
     * */
    /**
     * 팀 해체
     */
    @Operation(
            summary = "Get team",
            description = "Get a team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}")
    public ResponseDto<TeamDto.TeamInfo> getTeamInfo(
            @PathVariable String teamCode
    ) {
        TeamDto.TeamInfo response = teamManageService.getTeamInfo(teamCode);
        return new ResponseDto<>(response);
    }

}
