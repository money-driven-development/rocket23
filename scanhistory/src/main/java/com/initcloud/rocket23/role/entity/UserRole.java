package com.initcloud.rocket23.role.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.initcloud.rocket23.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roleName;

    @Column
    private Integer rolePriority;

    public UserRole(Long id, String roleName, Integer rolePriority) {
        this.id = id;
        this.roleName = roleName;
        this.rolePriority = rolePriority;
    }
}
