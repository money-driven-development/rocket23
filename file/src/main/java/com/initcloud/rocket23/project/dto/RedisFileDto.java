package com.initcloud.rocket23.project.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisFileDto {
	private String uuid;
	private String team;
	private String originName;
	private String project;
	private String fileType;
	private String provider;
	private String projectType;

	@Builder
	public RedisFileDto(String uuid, String originName, String team, String project, String fileType, String provider, String projectType) {
		this.uuid = uuid;
		this.team = team;
		this.originName = originName;
		this.project = project;
		this.fileType = fileType;
		this.provider = provider;
		this.projectType = projectType;
	}

	public static RedisFileDto toDto(String uuid, String originName, String teamCode, String projectCode) {
		return RedisFileDto.builder()
				.uuid(uuid)
				.team(teamCode)
				.originName(originName)
				.project(projectCode)
				.build();
	}
}
