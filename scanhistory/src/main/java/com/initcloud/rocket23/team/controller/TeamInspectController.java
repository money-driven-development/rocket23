package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.TeamMemberDto;
import com.initcloud.rocket23.team.service.TeamInspectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Team Inspect API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/team")
public class TeamInspectController {

    private final TeamInspectService teamInspectService;

    @Operation(
            summary = "Get Member List with Page",
            description = "Retrieve paged list of members.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "pageable", in = ParameterIn.QUERY, description = "paging value", required = true, schema = @Schema(implementation = Pageable.class))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/members")
    public ResponseDto<Page<TeamMemberDto.Summary>> memberList(
            final Pageable pageable,
            @PathVariable String teamCode
    ) {
        Page<TeamMemberDto.Summary> response = teamInspectService.getTeamMemberList(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Get Member details",
            description = "Retrieve specific member details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "memberEmail", in = ParameterIn.PATH, description = "member email", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/members/{memberEmail}")
    public ResponseDto<Object> memberDetails(
            @PathVariable String teamCode,
            @PathVariable String memberEmail // Todo - 추후 고유 유저명으로 변경할 예정
    ) {
        TeamMemberDto.Details response = teamInspectService.getTeamMemberDetails(teamCode, memberEmail);

        return new ResponseDto<>(response);
    }
}
