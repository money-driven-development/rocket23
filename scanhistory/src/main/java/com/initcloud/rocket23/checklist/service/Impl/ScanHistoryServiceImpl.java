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
	public ScanResultDto getScanHistory(String teamCode, String projectCode, String hashCode) {
		ScanHistory scanHistory = scanHistoryRepository.findTopByTeam_TeamCodeAndProject_ProjectCodeAndFileHashOrderById(
			teamCode,
			projectCode, hashCode).orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
		return ScanResultDto.builder()
			.scanHistory(scanHistory)
			.build();
	}

	/*
		단일 스캔 전체 내역 조회
	 */
	@Override
	public ScanResultDto getScanHistoryTotal(String teamCode, String projectCode, String hashCode) {
		ScanHistory scanHistory = scanHistoryRepository.findTopByTeam_TeamCodeAndProject_ProjectCodeAndFileHashOrderById(
			teamCode, projectCode, hashCode).orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
		List<ScanHistoryDetail> scanHistoryDetails = scanHistory.getScanDetails();

		return ScanResultDto.builder()
			.scanHistory(scanHistory)
			.scanHistoryDetails(scanHistoryDetails)
			.build();
	}

	/*
		단일 스캔 내역 페이징 처리
	 */
	@Override
	public Page<ScanResultDto.Summary> getScanHistoryPaging(String teamCode, String projectCode, Pageable pageable) {
		Page<ScanHistory> scanHistories = scanHistoryRepository.findAllByTeam_TeamCodeAndProject_ProjectCode(pageable,
			teamCode, projectCode);
		return scanHistories.map(ScanResultDto.Summary::new);
	}

	/*
		단일 스캔에서 실패 내역 조회.
	 */
	@Override
	public ScanFailDetailDto getScanFailDetail(String teamCode, String projectCode, String hashCode) {
		ScanHistory scanHistory = scanHistoryRepository.findTopByTeam_TeamCodeAndProject_ProjectCodeAndFileHashOrderById(
			teamCode,
			projectCode, hashCode).orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
		List<ScanHistoryDetail> scanHistoryDetails = scanHistory.getScanDetails()
			.stream()
			.filter(scanHistoryDetail -> "failed".equals(scanHistoryDetail.getScanResult()))
			.collect(Collectors.toList());

		return new ScanFailDetailDto(scanHistory, scanHistoryDetails);
	}
	// @Override
	// public List<HistoryDto> getHistoryList(String teamId, String projectId) {
	// 	return null;
	// }
	//
	// @Override
	// public Page<HistoryDto> getOffsetPageHistoryList(String teamId, String projectId, Pageable page) {
	// 	return null;
	// }
	//
	// @Override
	// public CursorResultDto getCursorPageHistoryList(String teamId, String cursorId, Pageable Page) {
	// 	return null;
	// }
}
