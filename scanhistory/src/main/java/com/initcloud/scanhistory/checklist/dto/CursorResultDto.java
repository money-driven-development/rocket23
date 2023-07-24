package com.initcloud.scanhistory.checklist.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class CursorResultDto {
	private List<ScanHistoryDto> values = new ArrayList<>();
	private Boolean hasNext;

	public CursorResultDto(List<ScanHistoryDto> values, Boolean hasNext) {
		this.values = values;
		this.hasNext = hasNext;
	}
}
