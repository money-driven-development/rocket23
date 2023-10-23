package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.TeamProjectCreateDto;
import com.initcloud.rocket23.team.dto.TeamProjectDto;
import com.initcloud.rocket23.team.service.TeamProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Team Project API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/team")
public class TeamProjectController {

    private final TeamProjectService teamProjectService;

    /**
     * 프로젝트 목록 조회
     */
    @Operation(
            summary = "Get Project list with Page",
            description = "Retrieve paged list of projects",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "pageable", in = ParameterIn.QUERY, description = "paging value", required = true, schema = @Schema(implementation = Pageable.class))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/projects")
    public ResponseDto<Page<TeamProjectDto.Summary>> projectPagedList(
            final Pageable pageable,
            @PathVariable String teamCode
    ) {
        Page<TeamProjectDto.Summary> response = teamProjectService.getPagedTeamProjects(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    /**
     * 프로젝트 목록 조회
     */
    @Operation(
            summary = "Get Project list",
            description = "Retrieve all list of projects",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/projects/all")
    public ResponseDto<List<TeamProjectDto.Summary>> projectList(
            @PathVariable String teamCode
    ) {
        List<TeamProjectDto.Summary> response = teamProjectService.getTeamProjects(teamCode);

        return new ResponseDto<>(response);
    }

    /**
     * 프로젝트 조회 프로젝트 별 대시보드 성격을 갖게 될 것임.
     */
    @Operation(
            summary = "Get Project details",
            description = "Retrieve a project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "projectCode", in = ParameterIn.PATH, description = "Project unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/projects/{projectCode}")
    public ResponseDto<TeamProjectDto.Details> projectDetails(
            @PathVariable String teamCode,
            @PathVariable String projectCode
    ) {
        TeamProjectDto.Details response = teamProjectService.getTeamProjectDetails(teamCode, projectCode);

        return new ResponseDto<>(response);
    }

    /**
     * 프로젝트 추가
     */
    @Operation(
            summary = "Add project",
            description = "Add a project to team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TeamProjectCreateDto.class)), description = "Create-Project Info", required = true)
    @PostMapping("/{teamCode}/projects")
    public ResponseDto<String> projectAdd(
            @PathVariable String teamCode,
            @RequestBody TeamProjectCreateDto request
    ) {
        String response = teamProjectService.createTeamProject(teamCode, request);

        return new ResponseDto<>(response);
    }

    /**
     * 프로젝트 삭제
     */
    @Operation(
            summary = "Remove project",
            description = "Remove a project from team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "projectCode", in = ParameterIn.PATH, description = "Project unique code", required = true, schema = @Schema(type = "string"))
    @DeleteMapping("/{teamCode}/projects/{projectCode}")
    public ResponseDto projectRemove(
            @PathVariable String teamCode,
            @PathVariable String projectCode
    ) {
        teamProjectService.removeTeamProject(teamCode, projectCode);

        return new ResponseDto<>(null);
    }
}
