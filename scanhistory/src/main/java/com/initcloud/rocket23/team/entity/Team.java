package com.initcloud.rocket23.team.entity;

import com.initcloud.rocket23.checklist.entity.ScanHistory;
import com.initcloud.rocket23.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEAM_ID")
	private Long id;

	@Column
	private String teamCode;

	@Column
	private String name;

	@Column
	private String logoUri;

	@OneToMany(mappedBy = "team")
	private List<ScanHistory> scanHistories = new ArrayList<>();

	public Team(Long id, String teamCode, String name, String logoUri) {
		this.id = id;
		this.teamCode = teamCode;
		this.name = name;
		this.logoUri = logoUri;
	}
}
