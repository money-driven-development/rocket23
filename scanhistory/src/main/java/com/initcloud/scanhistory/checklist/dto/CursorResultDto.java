package com.initcloud.scanhistory.checklist.dto;

import java.util.List;

public class CursorResultDto {
	private List<ScanHistoryDto> values;
	private boolean hasNext;

	public CursorResultDto(List<ScanHistoryDto> values, Boolean hasNext){
		this.values = values;
		this.hasNext = hasNext;
	}
}
