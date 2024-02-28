package com.initcloud.rocket23.team.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.InviteRepository;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.entity.Invite;
import com.initcloud.rocket23.team.entity.TeamWithUsers;
import com.initcloud.rocket23.team.enums.InviteState;
import java.util.List;


import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamInviteService {

    private final TeamWithUsersRepository teamWithUsersRepository;
    private final InviteRepository inviteRepository;

    /**
     * 각 유저별 초대 대기 목록 페이지 제공
     * */
    public List<Invite> userInviteWaitList(String userEmail) {
        List<Invite> allInvites = inviteRepository.findByUser_Email(userEmail);

        return allInvites.stream()
                .filter(invite -> InviteState.wait.equals(invite.getInviteState()))
                .collect(Collectors.toList());
    }

    /**
     * 유저 초대 승인 및 거절
     * */
    public void acceptInvite(TeamInviteDto.acceptRequest dto){
        Invite invite = getInvite(dto.getEmail(), dto.getTeamCode());
        if(dto.isAccept()) {
            TeamWithUsers teamWithUsers = TeamWithUsers.builder()
                    .user(invite.getUser())
                    .team(invite.getTeam())
                    .build();
            teamWithUsersRepository.save(teamWithUsers);

            invite.updateState(InviteState.approve);
        }else{
            invite.updateState(InviteState.deny);
        }
        inviteRepository.save(invite);

    }

    /**
     * 초대 객체 가져오기
     * */
    public Invite getInvite(String email, String teamCode){
        List<Invite> invites = inviteRepository.findByUser_EmailAndTeam_TeamCode(email, teamCode);
        return invites.stream()
                .filter(waitInvite -> InviteState.wait.equals(waitInvite.getInviteState()))
                .findFirst()
                .orElseThrow(
                        () -> new ApiException(ResponseCode.INVALID_REQUEST)
                );
    }
}
