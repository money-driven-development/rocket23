package com.initcloud.scanhistory.checklist.service;

import java.util.List;

import com.initcloud.scanhistory.checklist.dto.HistoryDto;

public interface ScanHistoryService {
	//List<ScanHistoryDto> getFileHistoryList(String fileHash);

	List<HistoryDto> getHistoryList(String teamId);

	/*
	CursorResultDto getPageHistoryList(Long cursorId, Pageable Page);
	*/
}
