package com.initcloud.scanhistory.common.enums;

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
	SERVER_CREATED_DIR_ERROR(5201, HttpStatus.INTERNAL_SERVER_ERROR, "디렉토리를 생성할 수 없습니다."),
	SERVER_STORE_ERROR(5202, HttpStatus.INTERNAL_SERVER_ERROR, "서버 저장 오류입니다."),
	FILE_EMPTY(5203, HttpStatus.INTERNAL_SERVER_ERROR, "빈 파일입니다."),
	AWS_FILE_UPLOAD_ERROR(5204, HttpStatus.INTERNAL_SERVER_ERROR, "AWS 파일 업로드 오류입니다."),
	FILE_WRONG_ERROR(5205,HttpStatus.INTERNAL_SERVER_ERROR,"파일이 올바르지 않습니다."),
	ZIP_PATH_ERROR(5206,HttpStatus.INTERNAL_SERVER_ERROR,"zip 파일 경로가 올바르지 않습니다."),
	ZIP_ENCODING_ERROR(5207,HttpStatus.INTERNAL_SERVER_ERROR,"압축파일 인코딩 오류입니다."),
	JSON_PROCESSING_ERROR(5208,HttpStatus.INTERNAL_SERVER_ERROR,"Redis Publish JSON 직렬화 오류입니다.");
	/*Scan history Error. 5400*/

	private final int code;
	private final HttpStatus httpStatus;
	private final String message;
}