package com.initcloud.scanhistory.checklist.service;

import java.awt.print.Pageable;
import java.util.List;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.dto.ScanHistoryDto;

public interface ScanHistoryService {
	List<ScanHistoryDto> getFileHistoryList(String fileHash);

	List<ScanHistoryDto> getHistoryList();

	CursorResultDto getPageHistoryList(Long cursorId, Pageable Page);
}
