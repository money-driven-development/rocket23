package com.initcloud.rocket23.team.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.InviteRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.team.dto.TeamDto;
import com.initcloud.rocket23.team.dto.TeamDto.TeamInfo;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.entity.Invite;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamWithUsers;
import com.initcloud.rocket23.team.enums.InviteState;
import com.initcloud.rocket23.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamManageService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;
    private final TeamWithUsersRepository teamWithUsersRepository;
    private final TeamInspectService teamInspectService;

    /**
     * [String] 유저를 팀으로 초대
     * Todo - 현재는 DB로 관리하지만 만료 기한을 두고 캐시로 관리 예정
     * Todo - QueryDSL 또는 JPQL 로 단일 쿼리로 Join 하여 실행할 예정
     */
    public String inviteUser(String teamCode, TeamInviteDto.Request dto) {

        Team team = teamRepository.findByTeamCode(dto.getTeamCode())
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        Optional<User> user = userRepository.findUserByEmail(dto.getEmail());

        if (user.isPresent()) {
            Invite newInvite = new Invite(user.get(), team, InviteState.wait);
            inviteRepository.save(newInvite);
        }

        return dto.getEmail();
    }

    /**
     * [boolean] 멤버를 팀에서 탈퇴 시킴
     * Todo - QueryDSL 또는 JPQL 로 단일 쿼리로 Join 하여 실행할 예정
     */
    public boolean removeMemberFromTeam(String teamCode, String memberEmail) {

        Team team = teamRepository.findByTeamCode(teamCode)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

        Optional<User> user = userRepository.findUserByEmail(memberEmail);

        if (user.isPresent()) {
            Optional<TeamWithUsers> remove = teamWithUsersRepository.findTeamWithUsersByTeamAndUser(team, user.get());
            if (remove.isPresent()) {
                teamWithUsersRepository.delete(remove.get());
            }

            return remove.isPresent();
        }

        return false;
    }

    /**
     * []
     */
    public String addTeam(TeamDto.Team dto) {
        Team team = teamRepository.save(new Team(dto));

        return team.getTeamCode();
    }

    /**
     * [boolean] 팀 해체
     * Todo - 팀이 무조건적으로 해체되는 것이 아니라 해체되는 조건을 지정해야 함.
     */
    public boolean removeTeam(String teamCode) {
        return teamRepository.deleteTeamByTeamCode(teamCode);
    }

    /**
     * 팀 정보 조회
     * */
    public TeamDto.TeamInfo getTeamInfo(String teamCode){
        Team team = teamInspectService.getTeam(teamCode);
        return new TeamInfo(team.getName(), team.getDescription(), team.getTeamCode());

    }

}
