package com.initcloud.rocket23.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.role.entity.UserRoleOfTeam;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleOfTeamRepository extends JpaRepository<UserRoleOfTeam, Long> {
}
