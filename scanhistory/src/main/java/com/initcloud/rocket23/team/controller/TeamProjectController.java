package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.team.dto.TeamProjectDto;
import com.initcloud.rocket23.team.service.TeamProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.initcloud.rocket23.common.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamProjectController {

    private TeamProjectService teamProjectService;

    @GetMapping("/{teamCode}/projects")
    public ResponseDto<Page<TeamProjectDto.Summary>> projectList(
            final Pageable pageable,
            @PathVariable String teamCode
    ) {
        Page<TeamProjectDto.Summary> response = teamProjectService.getTeamProjects(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    /**
     *  프로젝트 별 대시보드 성격을 갖게 될 것임.
     */
    @GetMapping("/{teamCode}/projects/{projectCode}")
    public ResponseDto<TeamProjectDto.Details> projectDetails(
            @PathVariable String teamCode,
            @PathVariable String projectCode
    ) {
        TeamProjectDto.Details response = teamProjectService.getTeamProjectDetails(teamCode, projectCode);

        return new ResponseDto<>(response);
    }

    @PostMapping("/{teamCode}/projects")
    public ResponseDto<String> projectAdd(
            @PathVariable String teamCode,
            @RequestBody TeamProjectDto.Create request
    ) {
        String response = teamProjectService.createTeamProject(teamCode, request);

        return new ResponseDto<>(response);
    }

    @DeleteMapping("/{teamCode}/projects")
    public ResponseDto projectRemove(
            @PathVariable String teamCode,
            @PathVariable String projectCode
    ) {
        teamProjectService.removeTeamProject(teamCode, projectCode);

        return new ResponseDto<>(null);
    }
}
