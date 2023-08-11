package com.initcloud.rocket23.checklist.service.Impl;

import com.initcloud.rocket23.checklist.dto.CursorResultDto;
import com.initcloud.rocket23.checklist.dto.HistoryDto;
import com.initcloud.rocket23.checklist.dto.ScanFailDetailDto;
import com.initcloud.rocket23.checklist.dto.ScanResultDto;
import com.initcloud.rocket23.checklist.entity.ScanHistory;
import com.initcloud.rocket23.checklist.entity.ScanHistoryDetail;
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
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

@RequiredArgsConstructor
@Service
public class ScanHistoryServiceImpl implements ScanHistoryService {

	private final ScanHistoryRepository scanHistoryRepository;

	/*
		단일 스캔 내역 조회
	 */
	@Override
	public ScanResultDto getScanHistory(Long teamCode, Long projectCode, String hashCode) {
		ScanHistory scanHistory = scanHistoryRepository.findTopByTeamIdAndProjectIdAndFileHashOrderById(
			teamCode,
			projectCode, hashCode).orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
		return new ScanResultDto(scanHistory);
	}

	/*
		단일 스캔에서 실패 내역 조회.
	 */
	@Override
	public ScanFailDetailDto getScanFailDetail(Long teamCode, Long projectCode, String hashCode) {
		ScanHistory scanHistory = scanHistoryRepository.findTopByTeamIdAndProjectIdAndFileHashOrderById(
			teamCode,
			projectCode, hashCode).orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
		List<ScanHistoryDetail> scanHistoryDetails = scanHistory.getScanDetails()
			.stream()
			.filter(scanHistoryDetail -> "failed".equals(scanHistoryDetail.getScanResult()))
			.collect(Collectors.toList());

		return new ScanFailDetailDto(scanHistory, scanHistoryDetails);
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
