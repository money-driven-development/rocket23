package com.initcloud.rocket23.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByUsername(String username);

	Optional<User> findUserByEmail(String email);
}
