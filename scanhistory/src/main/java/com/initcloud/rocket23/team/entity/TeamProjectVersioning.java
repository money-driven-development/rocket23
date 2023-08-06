package com.initcloud.rocket23.team.entity;


import com.initcloud.rocket23.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProjectVersioning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 버저닝 대상 프로젝트
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TeamProject project;

    /**
     * 이전 버전 프로젝트
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TeamProjectVersioning parent;

    /**
     * 프로젝트 등록/스캔 수행자
     */
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String name;

    @Column
    private String path;

    @Deprecated
    @Column
    private String storageType;

    @Column
    private String versionHashSHA1;

    @Column
    private String versionHashSHA2;
}
