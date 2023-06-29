package com.initcloud.dockerapi.common.enums;

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
	INVALID_REQUEST(4001, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

	/**
	 * 42xx: File Exception
	 */

	/**
	 * 43xx: Container Exception
	 * @Todo - 추후, 컨테이너 관련 예외는 4xx와 5xx 에러를 명확하게 구분해야 함.
	 */
	DOCKER_CANNOT_RUN_CONTAINER(4301, HttpStatus.BAD_REQUEST, "컨테이너를 실행할 수 없습니다."),
	DOCKER_CANNOT_GET_CONTAINER(4302, HttpStatus.BAD_REQUEST, "도커 컨테이너를 조회할 수 없습니다."),
	DOCKER_CANNOT_GET_CONTAINER_LIST(4303, HttpStatus.BAD_REQUEST, "도커 컨테이너 목록을 조회할 수 없습니다."),
	DOCKER_CANNOT_TERMINATE_CONTAINER(4304, HttpStatus.BAD_REQUEST, "도커 컨테이너를 종료할 수 없습니다."),
	DOCKER_IMAGE_NOT_FOUND(4305, HttpStatus.BAD_REQUEST, "도커 이미지가 존재하지 않습니다."),
	DOCKER_CANNOT_READ_SCAN_OUTPUT(4305, HttpStatus.BAD_REQUEST, "스캔 결과를 읽을 수 없습니다."),
	DOCKER_CANNOT_PARSE_SCAN_TO_JSON(4305, HttpStatus.BAD_REQUEST, "JSON 응답을 생성할 수 없습니다."),

	/* Server Error. */
	SERVER_BUSY(5001, HttpStatus.INTERNAL_SERVER_ERROR, "서버가 바쁩니다. 잠시 후 다시 시도해주세요."),

	/* 미확인 에러 */
	SERVER_ERROR(5100, HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다."),

	/**
	 * 53xx: Container Exception
	 * Todo - 추후, 컨테이너 관련 예외는 4xx와 5xx 에러를 명확하게 구분해야 함.
	 */
	NULL_DOCKER_CLIENT(5305, HttpStatus.INTERNAL_SERVER_ERROR, "실행 중인 도커 데몬 정보가 없습니다."),
	DOCKER_CANNOT_PULL_IMAGE(5306, HttpStatus.INTERNAL_SERVER_ERROR, "이미지 풀링 중 오류가 발생했습니다.");

	private final int code;
	private final HttpStatus httpStatus;
	private final String message;
}