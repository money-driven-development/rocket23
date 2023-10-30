package com.initcloud.rocket23.checklist.dto;

import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;

import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanHistoryDto {
    private String projectName;
    private String projectCode;
    private String projectHash;
    private Integer passed;
    private Integer skipped;
    private Integer failed;
    private Integer high;
    private Integer medium;
    private Integer low;
    private Integer unknown;
    private Integer cveCount;
    private Double score;

    public ScanHistoryDto(ScanHistory entity) {
        this.projectName = entity.getProjectName();
        this.projectCode = entity.getProjectCode();
        this.projectHash = entity.getProjectHash();
        this.passed = entity.getPassed();
        this.failed = entity.getFailed();
        this.skipped = entity.getSkipped();
        this.high = entity.getHigh();
        this.medium = entity.getMedium();
        this.low = entity.getLow();
        this.unknown = entity.getUnknown();
        this.cveCount = entity.getCveCount();
        this.score = entity.getScore();
    }
}
