package com.initcloud.dockerapi.common.dto;

import com.initcloud.dockerapi.common.enums.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionDto {
	private final int code;
	private final String message;

	@Builder
	public ExceptionDto(ResponseCode res) {
		this.code = res.getCode();
		this.message = res.getMessage();
	}
}
