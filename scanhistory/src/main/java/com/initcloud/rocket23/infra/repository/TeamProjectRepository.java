package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamProjectRepository extends JpaRepository<TeamProject, Long> {

    Optional<TeamProject> findTeamProjectByTeamAndProjectCode(Team team, String projectCode);

    Optional<TeamProject> findTeamProjectByTeam_TeamCodeAndProjectCode(String teamCode, String projectCode);

    Page<TeamProject> findTeamProjectsByTeam(Pageable pageable, Team team);

    List<TeamProject> findTeamProjectsByTeam(Team team);

    void deleteTeamProjectByTeamAndProjectCode(Team team, String projectCode);
}
