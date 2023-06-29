package com.initcloud.rocket23.file.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisFileDto {
	private String uuid;
	private LocalDateTime createdAt;

	@Builder
	public RedisFileDto(String uuid, LocalDateTime createdAt) {
		this.uuid = uuid;
		this.createdAt = createdAt;
	}

	public static RedisFileDto toDto(String uuid) {
		return RedisFileDto.builder()
			.uuid(uuid)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
