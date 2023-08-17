package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.team.dto.TeamProjectDto;
import com.initcloud.rocket23.team.service.TeamProjectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.initcloud.rocket23.common.dto.ResponseDto;

@ApiOperation("Team Project API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamProjectController {

    private TeamProjectService teamProjectService;

    /**
     * 프로젝트 목록 조회
     */
    @ApiOperation(value = "Get Project list with Page", notes = "Retrieve paged list of projects.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageable", paramType = "query", value = "paging value", required = true, dataTypeClass = Pageable.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/projects")
    public ResponseDto<Page<TeamProjectDto.Summary>> projectList(
            final Pageable pageable,
            @PathVariable String teamCode
    ) {
        Page<TeamProjectDto.Summary> response = teamProjectService.getTeamProjects(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    /**
     * 프로젝트 조회
     * 프로젝트 별 대시보드 성격을 갖게 될 것임.
     */
    @ApiOperation(value = "Get Project details", notes = "Retrieve a project.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "projectCode", paramType = "path", value = "Project unique code", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/projects/{projectCode}")
    public ResponseDto<TeamProjectDto.Details> projectDetails(
            @PathVariable String teamCode,
            @PathVariable String projectCode
    ) {
        TeamProjectDto.Details response = teamProjectService.getTeamProjectDetails(teamCode, projectCode);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Add project", notes = "Add a project to team.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "request", paramType = "body", value = "Create-Project Info", readOnly = true, dataTypeClass = TeamProjectDto.Create.class)})
    @PostMapping("/{teamCode}/projects")
    public ResponseDto<String> projectAdd(
            @PathVariable String teamCode,
            @RequestBody TeamProjectDto.Create request
    ) {
        String response = teamProjectService.createTeamProject(teamCode, request);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Remove project", notes = "Remove a project from team.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @DeleteMapping("/{teamCode}/projects")
    public ResponseDto projectRemove(
            @PathVariable String teamCode,
            @PathVariable String projectCode
    ) {
        teamProjectService.removeTeamProject(teamCode, projectCode);

        return new ResponseDto<>(null);
    }
}
