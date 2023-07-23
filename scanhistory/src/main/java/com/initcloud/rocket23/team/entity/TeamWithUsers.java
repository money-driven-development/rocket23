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
import com.initcloud.rocket23.team.dto.TeamMemberDto;
import com.initcloud.rocket23.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public static Page<TeamMemberDto.Summary> toPageDto(Page<TeamWithUsers> entities) throws NullPointerException {
        return entities.map(entity -> toSummaryDto(entity));
    }

    public static TeamMemberDto.Summary toSummaryDto(TeamWithUsers entity) throws NullPointerException {
        User user = entity.getUser();
        return TeamMemberDto.Summary
                .builder()
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .joinDate(entity.getCreatedAt().toLocalDate())
                .build();
    }

    public static TeamMemberDto.Details toDetailsDto(TeamWithUsers entity) throws NullPointerException {
        User user = entity.getUser();
        return TeamMemberDto.Details
                .builder()
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .joinDate(entity.getCreatedAt().toLocalDate())
                .contact(user.getContact())
                .lastLogin(user.getLastLogin())
                .isAdmin(false) // Todo
                .role(null)     // Todo
                .build();
    }
}
