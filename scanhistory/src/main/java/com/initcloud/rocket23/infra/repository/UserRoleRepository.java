package com.initcloud.rocket23.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.role.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
