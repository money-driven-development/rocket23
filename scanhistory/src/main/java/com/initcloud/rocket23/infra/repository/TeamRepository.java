package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamCode(String teamCode);

    boolean deleteTeamByTeamCode(String teamCode);
}
