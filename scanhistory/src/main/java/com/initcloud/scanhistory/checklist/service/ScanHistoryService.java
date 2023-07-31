package com.initcloud.scanhistory.checklist.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.initcloud.scanhistory.checklist.dto.CursorResultDto;
import com.initcloud.scanhistory.checklist.dto.HistoryDto;

public interface ScanHistoryService {
	List<HistoryDto> getHistoryList(Long teamId,Long projectId);

	CursorResultDto getPageHistoryList(Long teamId, Long cursorId, Pageable Page);
}
