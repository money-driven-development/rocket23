package com.initcloud.dockerapi.common.exception;

import com.initcloud.dockerapi.common.enums.ResponseCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiAuthException extends RuntimeException {
	private final ResponseCode responseCode;
}

