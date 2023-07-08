package com.initcloud.scanhistory.common.dto;

import com.initcloud.scanhistory.common.enums.ResponseCode;

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
