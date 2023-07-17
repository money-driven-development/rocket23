package com.initcloud.rocket23.team.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamWithUsers extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAdmin;

    private String roleType;

    private String authorities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public TeamWithUsers(Long id, Boolean isAdmin, String roleType, String authorities, Team team, User user) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.roleType = roleType;
        this.authorities = authorities;
        this.team = team;
        this.user = user;
    }
}
