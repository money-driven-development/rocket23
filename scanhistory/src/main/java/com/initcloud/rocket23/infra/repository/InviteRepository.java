package com.initcloud.rocket23.infra.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.team.entity.Invite;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    Invite findByUser_UsernameAndTeam_TeamCode(String username, String teamCode);

    List<Invite> findByUser_Username(String username);
}