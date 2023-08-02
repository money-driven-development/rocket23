package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.team.dto.TeamMemberDto;
import com.initcloud.rocket23.team.service.TeamInspectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.initcloud.rocket23.common.dto.ResponseDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamInspectController {

    private final TeamInspectService teamInspectService;

    @GetMapping("/{teamCode}/members")
    public ResponseDto<Page<TeamMemberDto.Summary>> memberList(
            final Pageable pageable,
            @PathVariable String teamCode
    ) {
        Page<TeamMemberDto.Summary> response = teamInspectService.getTeamMemberList(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    @GetMapping("/{teamCode}/members/{memberEmail}")
    public ResponseDto<Object> memberDetails(
            @PathVariable String teamCode,
            @PathVariable String memberEmail // Todo - 추후 고유 유저명으로 변경할 예정
    ) {
        TeamMemberDto.Details response = teamInspectService.getTeamMemberDetails(teamCode, memberEmail);

        return new ResponseDto<>(response);
    }
}
