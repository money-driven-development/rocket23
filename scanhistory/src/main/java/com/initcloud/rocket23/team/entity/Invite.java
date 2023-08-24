package com.initcloud.rocket23.team.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INVITE_ID")
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