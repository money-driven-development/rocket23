package com.initcloud.rocket23.checklist.service.Impl;

import com.initcloud.rocket23.checklist.dto.CursorResultDto;
import com.initcloud.rocket23.checklist.dto.HistoryDto;
import com.initcloud.rocket23.checklist.dto.ScanResultDto;
import com.initcloud.rocket23.checklist.entity.ScanHistory;
import com.initcloud.rocket23.checklist.service.ScanHistoryService;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.ScanHistoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@RequiredArgsConstructor
@Service
public class ScanHistoryServiceImpl implements ScanHistoryService {

	private final ScanHistoryRepository scanHistoryRepository;

	@Override
	public ScanResultDto getScanHistory(Long teamCode, Long projectCode, String hashCode) {
		Optional<ScanHistory> scanHistory = scanHistoryRepository.findTopByTeamIdAndProjectIdAndFileHashOrderById(
			teamCode,
			projectCode, hashCode);
		if (!scanHistory.isPresent()) {
			throw new ApiException(ResponseCode.NO_SCAN_RESULT);
		}
		return new ScanResultDto(scanHistory.get());
	}

	// @Override
	// public List<HistoryDto> getHistoryList(Long teamId, Long projectId) {
	// 	return null;
	// }
	//
	// @Override
	// public Page<HistoryDto> getOffsetPageHistoryList(Long teamId, Long projectId, Pageable page) {
	// 	return null;
	// }
	//
	// @Override
	// public CursorResultDto getCursorPageHistoryList(Long teamId, Long cursorId, Pageable Page) {
	// 	return null;
	// }
}
