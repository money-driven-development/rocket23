package com.initcloud.rocket23.common.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

	/* Invalid Request */
	/**
	 * 4000 ~ 4199: Common, Auth Exception
	 */
	INVALID_REQUEST(4001, HttpStatus.BAD_REQUEST, "Invalid Request."),

	/**
	 * 42xx: File Exception
	 */

	/**
	 * 43xx: Docker API Exception
	 */

	/* Server Error. */
	SERVER_BUSY(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Server busy."),
	SCAN_ERROR(5002, HttpStatus.INTERNAL_SERVER_ERROR, "Scan Error."),
	SERVER_ERROR(5100, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error.");

	private final int code;
	private final HttpStatus httpStatus;
	private final String message;
}