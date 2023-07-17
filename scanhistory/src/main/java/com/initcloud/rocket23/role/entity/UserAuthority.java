package com.initcloud.rocket23.role.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.initcloud.rocket23.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthority extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorityName;

    private String authorityType;

    private Integer authorityPriority;

    public UserAuthority(Long id, String authorityName, String authorityType, Integer authorityPriority) {
        this.id = id;
        this.authorityName = authorityName;
        this.authorityType = authorityType;
        this.authorityPriority = authorityPriority;
    }
}
