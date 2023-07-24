package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.team.entity.TeamWithUsers;

import java.awt.print.Pageable;
import java.util.Optional;


public interface TeamWithUsersRepository extends JpaRepository<TeamWithUsers, Long> {

    Page<TeamWithUsers> findAllByTeam(Pageable pageable, Team team);

    Optional<TeamWithUsers> findTeamWithUsersByByTeam(Team team);
    Optional<TeamWithUsers> findTeamWithUsersByTeamAndUser(Team team, User user);
}
