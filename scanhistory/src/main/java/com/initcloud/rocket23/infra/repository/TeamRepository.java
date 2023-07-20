package com.initcloud.rocket23.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Optional<Team> findByTeamCode(String teamCode);
}
