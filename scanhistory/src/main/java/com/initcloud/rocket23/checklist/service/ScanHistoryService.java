package com.initcloud.rocket23.checklist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.initcloud.rocket23.checklist.dto.CursorResultDto;
import com.initcloud.rocket23.checklist.dto.HistoryDto;
import com.initcloud.rocket23.checklist.dto.ScanFailDetailDto;
import com.initcloud.rocket23.checklist.dto.ScanResultDto;

public interface ScanHistoryService {
	ScanResultDto getScanHistory(Long teamId, Long projectId, String fileHash);

	ScanFailDetailDto	getScanFailDetail(Long teamId, Long projectId, String fileHash);
	// List<HistoryDto> getHistoryList(Long teamId, Long projectId);
	//
	// Page<HistoryDto> getOffsetPageHistoryList(Long teamId, Long projectId, Pageable page);
	//
	// CursorResultDto getCursorPageHistoryList(Long teamId, Long cursorId, Pageable Page);
}
