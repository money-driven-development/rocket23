package com.initcloud.rocket23.checklist.entity.scanHistory;

import com.initcloud.rocket23.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Table(name = "SCAN_HISTORY_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistoryDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCANHISTORY_DETAIL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HISTORY_ID")
    private ScanHistory scanHistory;

    @Column(name = "CHECK_TYPE")
    @NotNull
    private String checkType;

    @Column(name = "APP_TYPE")
    @NotNull
    private String appType;

    @Column(name = "SCAN_RESULT")
    @NotNull
    private String scanResult;

    @Column(name = "TARGET_FILE_NAME")
    @NotNull
    private String targetFileName;

    @Column(name = "RESOURCE")
    @NotNull
    private String resource;

    @Column(name = "RESOURCE_NAME")
    @NotNull
    private String resourceName;

    @Column(name = "RULE_NAME")
    @NotNull
    private String ruleName;

    @Column(name = "LINE")
    @NotNull
    private String line;

    @Column(name = "CODE")
    @NotNull
    private String code;

    @Builder
    public ScanHistoryDetail(ScanHistory scanHistory, String checkType,
                             String targetFileName, String appType, String scanResult, String line, String code,
                             String resource,
                             String resourceName, String ruleName) {
        this.scanHistory = scanHistory;
        this.checkType = checkType;
        this.targetFileName = targetFileName;
        this.appType = appType;
        this.scanResult = scanResult;
        this.line = line;
        this.code = code;
        this.resource = resource;
        this.resourceName = resourceName;
        this.ruleName = ruleName;
        scanHistory.getScanDetails().add(this);
    }
}
