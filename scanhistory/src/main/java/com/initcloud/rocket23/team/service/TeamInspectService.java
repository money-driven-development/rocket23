package com.initcloud.rocket23.team.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.team.dto.TeamMemberDto;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamWithUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamInspectService {

    private final TeamRepository teamRepository;
    private final TeamWithUsersRepository teamWithUsersRepository;

    /**
     * [Page<TeamMemberDto.Summary>] 팀 멤버 목록 조회
     */
    public Page<TeamMemberDto.Summary> getTeamMemberList(Pageable pageable, String teamCode) {
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        Page<TeamWithUsers> users = teamWithUsersRepository.findByTeam(pageable, team);

        return TeamWithUsers.toPageDto(users);
    }

    /**
     * [TeamMemberDto.Details] 팀 멤버 세부 조회
     */
    public TeamMemberDto.Details getTeamMemberDetails(String teamCode, String email) {
        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        TeamWithUsers teamWithUser = teamWithUsersRepository.findTeamWithUsersByTeam(team)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER_IN_TEAM));

        return TeamWithUsers.toDetailsDto(teamWithUser);
    }
}
