package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.entity.Invite;
import com.initcloud.rocket23.team.service.TeamInviteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Team Invite API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/invite")
public class TeamInviteController {

    private final TeamInviteService teamInviteService;

    /**
     * 사용자 팀 초대 승인
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
    @PostMapping()
    public ResponseDto<Boolean> memberInvite(
            @RequestBody TeamInviteDto.acceptRequest dto
    ) {
        teamInviteService.acceptInvite(dto);
        return new ResponseDto<>(true);
    }

    /**
     * 사용자별 초대 목록 조회
     */
    @Operation(
            summary = "Invite Team List",
            description = "Invite Team List",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "username", in = ParameterIn.PATH, description = "User's name", required = true, schema = @Schema(type = "string"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TeamInviteDto.Request.class)), description = "Request info", required = true)
    @GetMapping("/members/{username}")
    public ResponseDto<List<Invite>> userInviteList(
            @PathVariable String username
    ) {
        List<Invite> inviteList =  teamInviteService.userInviteList(username);
        return new ResponseDto<>(inviteList);
    }



}
