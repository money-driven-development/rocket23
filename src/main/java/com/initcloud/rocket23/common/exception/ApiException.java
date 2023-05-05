package com.initcloud.rocket23.common.exception;

import com.initcloud.rocket23.common.enums.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {

	private final Throwable ex;
	private final ResponseCode responseCode;
}
