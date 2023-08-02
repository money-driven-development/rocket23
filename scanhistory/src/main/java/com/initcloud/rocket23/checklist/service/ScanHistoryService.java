package com.initcloud.rocket23.checklist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.initcloud.rocket23.checklist.dto.CursorResultDto;
import com.initcloud.rocket23.checklist.dto.HistoryDto;

public interface ScanHistoryService {
	List<HistoryDto> getHistoryList(Long teamId, Long projectId);

	Page<HistoryDto> getOffsetPageHistoryList(Long teamId, Long projectId, Pageable page);

	CursorResultDto getCursorPageHistoryList(Long teamId, Long cursorId, Pageable Page);
}
