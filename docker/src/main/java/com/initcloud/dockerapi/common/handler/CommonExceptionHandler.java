package com.initcloud.dockerapi.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.initcloud.dockerapi.common.dto.ResponseDto;
import com.initcloud.dockerapi.common.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity<Object> handleApi(ApiException exception) {
		return ResponseDto.toException(exception);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handle(Exception exception) {
		return ResponseDto.toException(exception);
	}
}
