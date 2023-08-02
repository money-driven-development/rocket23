package com.initcloud.rocket23.checklist.service.Impl;

import com.initcloud.rocket23.checklist.dto.CursorResultDto;
import com.initcloud.rocket23.checklist.dto.HistoryDto;
import com.initcloud.rocket23.checklist.service.ScanHistoryService;
import com.initcloud.rocket23.infra.repository.ScanHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScanHistoryServiceImpl implements ScanHistoryService {

	private final ScanHistoryRepository scanHistoryRepository;


	@Override
	public List<HistoryDto> getHistoryList(Long teamId, Long projectId) {
		return null;
	}

	@Override
	public Page<HistoryDto> getOffsetPageHistoryList(Long teamId, Long projectId, Pageable page) {
		return null;
	}

	@Override
	public CursorResultDto getCursorPageHistoryList(Long teamId, Long cursorId, Pageable Page) {
		return null;
	}
}
