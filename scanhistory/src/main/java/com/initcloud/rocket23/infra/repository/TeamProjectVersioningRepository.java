package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.team.entity.TeamProjectVersioning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamProjectVersioningRepository extends JpaRepository<TeamProjectVersioning, Long> {
}
