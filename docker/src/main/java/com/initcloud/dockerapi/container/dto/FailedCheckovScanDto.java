package com.initcloud.dockerapi.container.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class FailedCheckovScanDto extends CheckovScanDto.Checks {

	/**
	 * 2개의 원소를 가짐
	 * 정수, 문자열
	 */
	@JsonProperty("code_block")
	private final List<List<Object>> codeBlock;

	@JsonProperty("file_line_range")
	private final List<Integer> fileLineRange;

	@JsonProperty("check_class")
	private final String checkClass;

	@JsonProperty("vulnerability_details")
	private final String vulnerabilityDetails;

	@JsonProperty("connected_node")
	private final String connectedNode;

	public FailedCheckovScanDto(String checkId, String bcCheckId, String checkName, CheckResult checkResult,
		String filePath, String fileAbsPath, String repoFilePath, String resource, List<List<Object>> codeBlock,
		List<Integer> fileLineRange, String checkClass, String vulnerabilityDetails, String connectedNode) {
		super(checkId, bcCheckId, checkName, checkResult, filePath, fileAbsPath, repoFilePath, resource);
		this.codeBlock = codeBlock;
		this.fileLineRange = fileLineRange;
		this.checkClass = checkClass;
		this.vulnerabilityDetails = vulnerabilityDetails;
		this.connectedNode = connectedNode;
	}
}