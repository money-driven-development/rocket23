package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByUsername(String username);

	User findByUsername(String username);

	Optional<User> findUserByEmail(String email);
}
