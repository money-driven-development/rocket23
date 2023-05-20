package com.initcloud.rocket23.common.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

	/* Invalid Request */
	INVALID_REQUEST(4001, HttpStatus.BAD_REQUEST, "Invalid Request."),

	DATA_MISSING(4002, HttpStatus.INTERNAL_SERVER_ERROR, "BAD_REQUEST"),
	/* Server Error. */
	SERVER_BUSY(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Server busy."),
	SCAN_ERROR(5002, HttpStatus.INTERNAL_SERVER_ERROR, "Scan Error."),

	SERVER_ERROR(5100, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error."),
	/*File Uplaod Error. */
	SERVER_CREATED_DIR_ERROR(5101, HttpStatus.INTERNAL_SERVER_ERROR, "Could not create upload directory"),
	SERVER_STORE_ERROR(5102, HttpStatus.INTERNAL_SERVER_ERROR, "Could not store the file");

	private final int code;
	private final HttpStatus httpStatus;
	private final String message;
}