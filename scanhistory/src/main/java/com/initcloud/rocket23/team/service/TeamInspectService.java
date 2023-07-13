package com.initcloud.rocket23.team.service;

import org.springframework.stereotype.Service;

import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamInspectService {

	private final TeamRepository teamRepository;
	private final TeamWithUsersRepository teamWithUsersRepository;

	public void getTeamDetails() {
		//Todo
	}
}
