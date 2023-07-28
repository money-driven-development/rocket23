package com.initcloud.rocket23.user.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.rocket23.user.entity.UserOAuthToken;

@Repository
public interface OAuthTokenRepository extends JpaRepository<UserOAuthToken, Long> {
	@Override
	Optional<UserOAuthToken> findById(Long aLong);
}
