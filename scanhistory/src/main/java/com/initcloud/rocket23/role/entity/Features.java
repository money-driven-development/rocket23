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
public class Features extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String featureType;

    @Column
    private String featureName;

    @Column
    private String featureUri;

    public Features(Long id, String featureType, String featureName, String featureUri) {
        this.id = id;
        this.featureType = featureType;
        this.featureName = featureName;
        this.featureUri = featureUri;
    }
}
