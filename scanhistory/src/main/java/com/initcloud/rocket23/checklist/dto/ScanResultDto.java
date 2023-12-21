package com.initcloud.rocket23.checklist.dto;

import com.initcloud.rocket23.checklist.entity.scanHistory.CodeBlock;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistoryDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanResultDto {
    private String projectName;
    private Integer high;
    private Integer medium;
    private Integer low;
    private Integer unknown;
    private Double score;
    private Integer passed;
    private Integer failed;
    private Integer skipped;
    private LocalDateTime created_at;
    private List<Detail> scanResultDetailList = new ArrayList<>();

    @Builder
    public ScanResultDto(ScanHistory scanHistory, List<ScanHistoryDetail> scanHistoryDetails) {
        this.projectName = scanHistory.getProjectName();
        this.high = scanHistory.getHigh();
        this.medium = scanHistory.getMedium();
        this.low = scanHistory.getLow();
        this.unknown = scanHistory.getUnknown();
        this.score = scanHistory.getScore();
        this.passed = scanHistory.getPassed();
        this.failed = scanHistory.getFailed();
        this.skipped = scanHistory.getSkipped();
        this.created_at = scanHistory.getCreatedAt();
        if (scanHistoryDetails != null) {
            this.scanResultDetailList.addAll(scanHistoryDetails.stream()
                    .map(Detail::new)
                    .collect(Collectors.toList()));
        }
    }

    @Builder(builderClassName = "demoBuilder", builderMethodName = "scanResultDemoBuilder")
    public ScanResultDto(String fileName, Integer high, Integer medium, Integer low, Integer unknown, Double score,
                         Integer passed, Integer failed, Integer skipped, LocalDateTime created,
                         List<Detail> scanResultDetailList) {
        this.projectName = fileName;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.unknown = unknown;
        this.score = score;
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
        this.created_at = created;
        this.scanResultDetailList = scanResultDetailList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Detail {
        private String appType;
        private String scanResult;
        private String ruleName;
        private String ruleDescription;
        private String fileName;
        private String scanResource;
        private List<CodeBlock> codeBlock;

        public Detail(ScanHistoryDetail scanHistoryDetail) {
            this.appType = scanHistoryDetail.getAppType();
            this.scanResult = scanHistoryDetail.getScanResult();
            this.ruleName = scanHistoryDetail.getRuleName();
            this.ruleDescription = scanHistoryDetail.getRuleDescription();
            this.fileName = scanHistoryDetail.getTargetFileName();
            this.scanResource = scanHistoryDetail.getScanResource();
            this.codeBlock = scanHistoryDetail.getCodeBlock();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Summary {
        private String fileName;
        private Integer passed;
        private Integer skipped;
        private Integer failed;
        private Double score;
        private String scanHash;
        private LocalDateTime created_at;

        public Summary(ScanHistory entity) {
            this.fileName = entity.getProjectName();
            this.passed = entity.getPassed();
            this.skipped = entity.getSkipped();
            this.failed = entity.getFailed();
            this.score = entity.getScore();
            this.scanHash = entity.getScanHash();
            this.created_at = entity.getCreatedAt();
        }
    }
}
