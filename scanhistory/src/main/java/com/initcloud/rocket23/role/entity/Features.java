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
public class Features extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String featureType;

	private String featureName;

	private String featureUri;

	public Features(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, String featureType,
		String featureName, String featureUri) {
		super(createdAt, modifiedAt);
		this.id = id;
		this.featureType = featureType;
		this.featureName = featureName;
		this.featureUri = featureUri;
	}
}
