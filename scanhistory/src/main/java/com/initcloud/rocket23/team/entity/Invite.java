package com.initcloud.rocket23.team.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	private Team team;

	public Invite(User user, Team team) {
		this.user = user;
		this.team = team;
	}
}