package com.initcloud.rocket23.checklist.service.Impl;

import com.initcloud.rocket23.checklist.dto.ScanResultDto;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistoryDetail;
import com.initcloud.rocket23.checklist.service.ScanHistoryService;
import com.initcloud.rocket23.checklist.service.ScanSeverityService;
import com.initcloud.rocket23.common.enums.Policy.Severity;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.infra.repository.ScanHistoryRepository;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScanHistoryServiceImpl implements ScanHistoryService {

    private final ScanHistoryRepository scanHistoryRepository;
    private final BasePolicyRepository basePolicyRepository;
    private final ScanSeverityService scanSeverityService;

    /*
        단일 스캔 내역 조회
     */
    @Override
    public ScanResultDto getScanHistory(String teamCode, String projectCode, String hashCode) {
        ScanHistory scanHistory = scanHistoryRepository.findTopByTeam_TeamCodeAndProject_ProjectCodeAndScanHashOrderByIdDesc(
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
        ScanHistory scanHistory = scanHistoryRepository.findTopByTeam_TeamCodeAndProject_ProjectCodeAndScanHashOrderByIdDesc(
                teamCode, projectCode, hashCode).orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
        List<ScanHistoryDetail> scanHistoryDetails = scanHistory.getScanDetails();

        Map<ScanHistoryDetail, Severity> ruleNameMap = scanHistoryDetails.stream()
                .collect(Collectors.toMap(detail -> detail, this::getRuleNameForScanDetail));

        return ScanResultDto.builder()
                .scanHistory(scanHistory)
                .scanHistoryDetails(scanHistoryDetails)
                .severityMap(ruleNameMap)
                .build();
    }

    private Severity getRuleNameForScanDetail(ScanHistoryDetail scanHistoryDetail) {
        String ruleName = scanHistoryDetail.getRuleName();
        String ICRuleName = scanSeverityService.ckvToIc(ruleName);
        BasePolicy basePolicy = basePolicyRepository.findByDefaultPolicyNameIC(ICRuleName);

        // basePolicy가 null이 아닌 경우에만 Severity 설정
        if (basePolicy != null) {
            return basePolicy.getSeverity();
        }

        return Severity.none;
    }

    /*
        프로젝트 내 스캔 목록 조회 페이징 처리
     */
    @Override
    public Page<ScanResultDto.Summary> getScanHistoryPaging(String teamCode, String projectCode, Pageable pageable) {
        Page<ScanHistory> scanHistories = scanHistoryRepository.findAllByTeam_TeamCodeAndProject_ProjectCodeOrderByIdDesc(pageable,
                teamCode, projectCode);
        return scanHistories.map(ScanResultDto.Summary::new);
    }

    /*
        단일 스캔 내역 전체 조회
     */
    @Override
    public List<ScanResultDto.Summary> getScanHistoryAll(String teamCode, String projectCode) {
        List<ScanHistory> scanHistories = scanHistoryRepository.findAllByTeam_TeamCodeAndProject_ProjectCodeOrderByIdDesc(teamCode,
                projectCode);
        if (scanHistories.isEmpty()) {
            return Collections.emptyList();
        }
        return scanHistories.stream().map(ScanResultDto.Summary::new).collect(Collectors.toList());
    }

    @Override
    public boolean getScanSuccess(String teamCode, String projectCode, String fileHash) {
        List<ScanHistory> scanHistories = scanHistoryRepository.findAllByFileHash(fileHash);
        if (scanHistories.isEmpty()) {
            throw new ApiException(ResponseCode.NO_SCAN_RESULT);
        }
        return true;
    }

    /*
        단일 스캔에서 실패 내역 조회.
     */
    //@Override
    //public ScanFailDetailDto getScanFailDetail(String teamCode, String projectCode, String hashCode) {
    //    ScanHistory scanHistory = scanHistoryRepository.findTopByTeam_TeamCodeAndProject_ProjectCodeAndFileHashOrderById(
    //            teamCode,
    //            projectCode, hashCode).orElseThrow(() -> new ApiException(ResponseCode.NO_SCAN_RESULT));
    //    List<ScanHistoryDetail> scanHistoryDetails = scanHistory.getScanDetails()
    //            .stream()
    //            .filter(scanHistoryDetail -> "failed".equals(scanHistoryDetail.getScanResult()))
    //            .collect(Collectors.toList());
    //    return new ScanFailDetailDto(scanHistory, scanHistoryDetails);
    //}
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
