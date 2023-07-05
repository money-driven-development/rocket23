package com.initcloud.dockerapi.container.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.dockerapi.common.enums.ResponseCode;
import com.initcloud.dockerapi.common.exception.ApiException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckovScanDto {

	@JsonProperty("check_type")
	private final String checkType;

	@JsonProperty("results")
	private final Result results;

	@Getter
	@RequiredArgsConstructor
	public static class Result {
		@JsonProperty("passed_checks")
		private final List<Checks> passedChecks;

		@JsonProperty("failed_checks")
		private final List<FailedCheckovScanDto> failedChecks;

		@JsonProperty("skipped_checks")
		private final List<Object> skippedChecks;

		@JsonProperty("parsing_errors")
		private final List<Object> parsingErrors;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Checks {
		@JsonProperty("check_id")
		private final String checkId;

		@JsonProperty("bc_check_id")
		private final String bcCheckId;

		@JsonProperty("check_name")
		private final String checkName;

		@JsonProperty("check_result")
		private final CheckResult checkResult;

		@JsonProperty("file_path")
		private final String filePath;

		@JsonProperty("file_abs_path")
		private final String fileAbsPath;

		@JsonProperty("repo_file_path")
		private final String repoFilePath;

		@JsonProperty("resource")
		private final String resource;

		@Getter
		@RequiredArgsConstructor
		public static class CheckResult {
			private final String result;
			@JsonProperty("evaluated_keys")
			private final List evaluatedKeys;
		}
	}

	@Getter
	@RequiredArgsConstructor
	public static class Summary {
		private final int passed;
		private final int failed;
		private final int skipped;

		@JsonProperty("parsing_errors")
		private final int parsingErrors;

		@JsonProperty("resource_count")
		private final int resourceCount;

		@JsonProperty("checkov_version")
		private final String checkovVersion;
	}

	public static CheckovScanDto deserializeToScanDto(String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, CheckovScanDto.class);
		} catch (JsonProcessingException e) {
			throw new ApiException(ResponseCode.DOCKER_CANNOT_PARSE_SCAN_TO_JSON);
		}
	}
}
