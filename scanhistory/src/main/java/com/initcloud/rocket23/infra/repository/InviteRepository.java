package com.initcloud.rocket23.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.team.entity.Invite;

public interface InviteRepository extends JpaRepository<Invite, Long> {
}