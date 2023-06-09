package com.initcloud.dockerapi.common.exception;

import com.initcloud.dockerapi.common.enums.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {

	private Throwable ex;
	private final ResponseCode responseCode;
}
