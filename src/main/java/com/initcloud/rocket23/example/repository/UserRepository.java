package com.initcloud.rocket23.example.repository;

import com.initcloud.rocket23.example.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findFirst2ByUsernameLikeOrderByIDDesc(String name);
}
