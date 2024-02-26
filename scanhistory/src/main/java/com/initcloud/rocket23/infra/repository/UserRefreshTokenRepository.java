package com.initcloud.rocket23.infra.repository;

import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.entity.UserRefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);

    Optional<UserRefreshToken> findByUser(User user);
}
