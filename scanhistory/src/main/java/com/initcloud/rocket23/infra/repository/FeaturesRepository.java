package com.initcloud.rocket23.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.initcloud.rocket23.role.entity.Features;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturesRepository extends JpaRepository<Features, Long> {
}
