package com.initcloud.rocket23.team.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.InviteRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.team.dto.TeamInviteDto;
import com.initcloud.rocket23.team.entity.Invite;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamManageService {

	private final TeamRepository teamRepository;
	private final UserRepository userRepository;
	private final InviteRepository inviteRepository;
	private final TeamWithUsersRepository teamWithUsersRepository;

	/**
	 * 유저를 팀으로 초대
	 * Todo - 현재는 DB로 관리하지만 만료 기한을 두고 캐시로 관리할 예정
	 */
	public String inviteUser(TeamInviteDto.Request dto) {

		Team team = teamRepository.findByTeamCode(dto.getTeamCode())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_TEAM));

		Optional<User> user = userRepository.findUserByEmail(dto.getEmail());

		if (user.isPresent()) {
			Invite newInvite = new Invite(user.get(), team);
			inviteRepository.save(newInvite);
		}

		return dto.getEmail();
	}

	public void withdrawalTeam() {
		//Todo
	}
}
