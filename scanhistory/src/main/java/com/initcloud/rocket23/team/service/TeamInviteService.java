package com.initcloud.rocket23.team.service;

import com.initcloud.rocket23.infra.repository.InviteRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.entity.Invite;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamWithUsers;
import com.initcloud.rocket23.user.entity.User;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamInviteService {

    private final TeamWithUsersRepository teamWithUsersRepository;
    private final InviteRepository inviteRepository;

    /**
     * 각 유저별 초대 목록 페이지 제공
     * */
    public List<Invite> userInviteList(String username){
        return inviteRepository.findByUser_Username(username);
    }

    /**
     * 유저 초대 승인 및 거절
     * */
    public void acceptInvite(TeamInviteDto.acceptRequest dto){
        Invite invite = getInvite(dto.getUsername(), dto.getTeamCode());
        if(dto.isAccept()){
            TeamWithUsers teamWithUsers = TeamWithUsers.builder()
                    .user(invite.getUser())
                    .team(invite.getTeam())
                    .build();
            teamWithUsersRepository.save(teamWithUsers);
        }
        inviteRepository.delete(invite);
    }

    /**
     * 초대 객체 가져오기
     * */
    public Invite getInvite(String username, String teamCode){
        return inviteRepository.findByUser_UsernameAndTeam_TeamCode(username, teamCode);
    }


}
