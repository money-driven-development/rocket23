package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.team.entity.TeamWithUsers;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeamWithUsersRepository extends JpaRepository<TeamWithUsers, Long> {

    Page<TeamWithUsers> findByTeam(Pageable pageable, Team team);

    Optional<TeamWithUsers> findTeamWithUsersByTeam(Team team);
    Optional<TeamWithUsers> findTeamWithUsersByTeamAndUser(Team team, User user);
}
