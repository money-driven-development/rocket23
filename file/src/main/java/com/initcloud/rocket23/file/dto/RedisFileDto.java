package com.initcloud.rocket23.file.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisFileDto {
	private String uuid;

	@Builder
	public RedisFileDto(String uuid){
		this.uuid = uuid;
	}

	public static RedisFileDto toDto(String uuid){
		return RedisFileDto.builder()
			.uuid(uuid)
			.build();
	}
}
