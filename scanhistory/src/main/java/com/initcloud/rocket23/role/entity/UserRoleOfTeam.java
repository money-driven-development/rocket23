package com.initcloud.rocket23.role.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.initcloud.rocket23.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoleOfTeam extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UserAuthorityOfRole userAuthorityOfRole;

    public UserRoleOfTeam(Long id, UserRole userRole, UserAuthorityOfRole userAuthorityOfRole) {
        this.id = id;
        this.userRole = userRole;
        this.userAuthorityOfRole = userAuthorityOfRole;
    }
}
