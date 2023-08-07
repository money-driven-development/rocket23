package com.initcloud.rocket23.checklist.dto;

import com.initcloud.rocket23.checklist.entity.ScanHistory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanResultDto {
	private Integer passed;
	private Integer failed;
	private Integer skipped;

	public ScanResultDto(ScanHistory entity) {
		this.passed = entity.getPassed();
		this.failed = entity.getFailed();
		this.skipped = entity.getSkipped();
	}
}
