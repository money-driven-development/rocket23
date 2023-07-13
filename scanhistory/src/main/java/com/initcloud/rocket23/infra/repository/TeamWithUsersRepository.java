package com.initcloud.rocket23.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.team.entity.TeamWithUsers;

public interface TeamWithUsersRepository extends JpaRepository<TeamWithUsers, Long> {
}
