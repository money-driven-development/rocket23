package com.initcloud.rocket23.checklist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.initcloud.rocket23.checklist.dto.CursorResultDto;
import com.initcloud.rocket23.checklist.dto.HistoryDto;
import com.initcloud.rocket23.checklist.dto.ScanFailDetailDto;
import com.initcloud.rocket23.checklist.dto.ScanResultDto;

public interface ScanHistoryService {
	ScanResultDto getScanHistory(String teamCode, String projectCode, String fileHash);

	ScanResultDto getScanHistoryTotal(String teamCode, String projectCode, String fileHash);

	ScanFailDetailDto getScanFailDetail(String teamCode, String projectCode, String fileHash);

	Page<ScanResultDto.Summary> getScanHistoryPaging(String teamCode, String projectCode, Pageable pageable);

	List<ScanResultDto.Summary> getScanHistoryAll(String teamCode, String projectCode);
	// List<HistoryDto> getHistoryList(String teamCode, String projectCode,;
	//
	// Page<HistoryDto> getOffsetPageHistoryList(String teamCode, String projectCode, Pageable page);
	//
	// CursorResultDto getCursorPageHistoryList(String teamCode, String cursorId, Pageable Page);
}
