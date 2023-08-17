package com.initcloud.rocket23.common.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

	/* Invalid Request */
	TOKEN_EXPIRED(4000, HttpStatus.UNAUTHORIZED, "Expired Token."),
	INVALID_REQUEST(4001, HttpStatus.BAD_REQUEST, "Invalid Request."),
	INVALID_TOKEN(4002, HttpStatus.UNAUTHORIZED, "Invalid Token."),
	DATA_MISSING(4005, HttpStatus.INTERNAL_SERVER_ERROR, "BAD_REQUEST"),
	INVALID_USER(4008, HttpStatus.BAD_REQUEST, "Invalid User."),
	INVALID_TOKEN_FORMAT(4016, HttpStatus.UNAUTHORIZED, "Invalid Token format."),
	UNSUPPORTED_TOKEN(4017, HttpStatus.UNAUTHORIZED, "Unsupported Token."),
	INVALID_TOKEN_SIGNATURE(4018, HttpStatus.UNAUTHORIZED, "Invalid Token signature."),
	EMPTY_TOKEN_CLAIMS(4019, HttpStatus.UNAUTHORIZED, "Empty Token Claims."),
	NULL_TOKEN(4020, HttpStatus.UNAUTHORIZED, "Token is null"),

	/* Server Error. */
	SERVER_BUSY(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Server busy."),
	SCAN_ERROR(5002, HttpStatus.INTERNAL_SERVER_ERROR, "Scan Error."),
	SERVER_ERROR(5100, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error."),

	/*File Uplaod Error. */
	SERVER_CREATED_DIR_ERROR(5201, HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리를 생성할 수 없습니다."),
	SERVER_STORE_ERROR(5202, HttpStatus.INTERNAL_SERVER_ERROR, "서버 저장 오류입니다."),
	FILE_EMPTY(5203, HttpStatus.INTERNAL_SERVER_ERROR, "빈 파일입니다."),
	AWS_FILE_UPLOAD_ERROR(5204, HttpStatus.INTERNAL_SERVER_ERROR, "AWS 파일 업로드 오류입니다."),
	FILE_WRONG_ERROR(5205, HttpStatus.INTERNAL_SERVER_ERROR, "파일이 올바르지 않습니다."),
	ZIP_PATH_ERROR(5206, HttpStatus.INTERNAL_SERVER_ERROR, "zip 파일 경로가 올바르지 않습니다."),
	ZIP_ENCODING_ERROR(5207, HttpStatus.INTERNAL_SERVER_ERROR, "압축파일 인코딩 오류입니다."),

	/*Scan history Error. 5400*/

	/*Redis Error. 5500*/
	JSON_PROCESSING_ERROR(5501, HttpStatus.INTERNAL_SERVER_ERROR, "Redis Publish JSON 직렬화 오류입니다.");

	private final int code;
	private final HttpStatus httpStatus;
	private final String message;
}