package com.initcloud.rocket23.project.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisFileDto {
	private String uuid;
	private String originName;
	private LocalDateTime createdAt;

	@Builder
	public RedisFileDto(String uuid, String originName, LocalDateTime createdAt) {
		this.uuid = uuid;
		this.originName = originName;
		this.createdAt = createdAt;
	}

	public RedisFileDto(String uuid, String originName) {
		this.uuid = uuid;
		this.originName = originName;
		this.createdAt = LocalDateTime.now();
	}

	public RedisFileDto(String originName) {
		this.uuid = UUID.randomUUID().toString();
		this.originName = originName;
		this.createdAt = LocalDateTime.now();
	}

	public static RedisFileDto toDto(String uuid) {
		return RedisFileDto.builder()
			.uuid(uuid)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
