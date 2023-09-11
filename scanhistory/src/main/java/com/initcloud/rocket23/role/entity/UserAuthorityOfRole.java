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
import com.initcloud.rocket23.team.entity.TeamWithUsers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthorityOfRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TeamWithUsers userWithTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private UserAuthority userAuthority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Features features;

    public UserAuthorityOfRole(Long id, TeamWithUsers userWithTeam, UserAuthority userAuthority, Features features) {
        this.id = id;
        this.userWithTeam = userWithTeam;
        this.userAuthority = userAuthority;
        this.features = features;
    }
}
