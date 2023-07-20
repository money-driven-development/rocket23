package com.initcloud.rocket23.team.entity;

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
public class Team extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String teamCode;
	private String name;

	private String logoUri;

	public Team(Long id, String teamCode, String name, String logoUri) {
		this.id = id;
		this.teamCode = teamCode;
		this.name = name;
		this.logoUri = logoUri;
	}
}
