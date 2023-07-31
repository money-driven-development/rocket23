package com.initcloud.scanhistory.checklist.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class CursorResultDto {
	private List<HistoryDto> values = new ArrayList<>();
	private Boolean hasNext;

	public CursorResultDto(List<HistoryDto> values, Boolean hasNext) {
		this.values = values;
		this.hasNext = hasNext;
	}
}
