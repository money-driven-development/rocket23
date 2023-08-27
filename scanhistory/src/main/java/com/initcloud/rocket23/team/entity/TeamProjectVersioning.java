package com.initcloud.rocket23.team.entity;


import com.initcloud.rocket23.team.dto.TeamProjectDto;
import com.initcloud.rocket23.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamProjectVersioning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VERSION_ID")
    private Long id;

    /**
     * 버저닝 대상 프로젝트
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private TeamProject project;

    /**
     * 이전 버전 프로젝트 - 파일 자체의 변경을 의미함.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_VERSION_ID")
    private TeamProjectVersioning parent;

    /**
     * 프로젝트 등록/스캔 수행자
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<TeamProjectVersioning> children = new ArrayList<>();

    @Builder
    public TeamProjectVersioning(Long id, TeamProject project, TeamProjectVersioning parent, User user, String name, String path, String storageType, String versionHashSHA1, String versionHashSHA2, List<TeamProjectVersioning> children) {
        this.id = id;
        this.project = project;
        this.parent = parent;
        this.user = user;
        this.name = name;
        this.path = path;
        this.storageType = storageType;
        this.versionHashSHA1 = versionHashSHA1;
        this.versionHashSHA2 = versionHashSHA2;
        this.children = children;
    }

    public TeamProjectDto.Version toDefaultDto() {
        return new TeamProjectDto.Version(parent);
    }
}
