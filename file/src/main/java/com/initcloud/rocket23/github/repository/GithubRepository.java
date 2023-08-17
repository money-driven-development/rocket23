package com.initcloud.rocket23.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.initcloud.rocket23.github.entity.GithubEntity;

@Repository
public interface GithubRepository extends JpaRepository<GithubEntity, Long> {
}
