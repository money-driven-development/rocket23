package com.initcloud.rocket23.checklist.dto;

import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryDto {
    private Long id;
    private String projectName;
    private String projectCode;
    private String projectHash;
    private LocalDateTime scanDateTime;
    private Double score;
    private Integer failed;
    private Integer passed;
    private Integer skipped;

    public HistoryDto(ScanHistory entity) {
        this.id = entity.getId();
        this.projectName = entity.getProjectName();
        this.projectCode = entity.getProjectCode();
        this.scanDateTime = entity.getCreatedAt();
        this.score = entity.getScore();
        this.failed = entity.getFailed();
        this.passed = entity.getPassed();
        this.skipped = entity.getSkipped();
    }

    @Builder
    public HistoryDto(Long id, String projectName, String projectCode, String projectHash, String provider,
                      LocalDateTime scanDateTime,
                      Double score, Integer failed, Integer passed, Integer skipped) {
        this.id = id;
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.projectHash = projectHash;
        this.scanDateTime = scanDateTime;
        this.score = score;
        this.failed = failed;
        this.passed = passed;
        this.skipped = skipped;
    }

}