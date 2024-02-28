package com.initcloud.rocket23.team.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.team.enums.InviteState;
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

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private InviteState inviteState;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	@JsonIgnore
	private Team team;

	public Invite(User user, Team team, InviteState inviteState) {
		this.inviteState = inviteState;
		this.user = user;
		this.team = team;
	}

	public void updateState(InviteState inviteState){
		this.inviteState = inviteState;
	}
}