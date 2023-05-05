package com.initcloud.rocket23.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.common.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity<Object> handleApi(ApiException exception) {
		log.error("ApiException: {}", exception.getEx().getMessage());
		return ResponseDto.toException(exception);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handle(Exception exception) {
		log.error("Exception: {}", exception.getMessage());
		return ResponseDto.toException(exception);
	}
}
