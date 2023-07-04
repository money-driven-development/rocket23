package com.initcloud.scanhistory.common.exception;


import com.initcloud.scanhistory.common.enums.ResponseCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiAuthException extends RuntimeException {
	private final ResponseCode responseCode;
}

