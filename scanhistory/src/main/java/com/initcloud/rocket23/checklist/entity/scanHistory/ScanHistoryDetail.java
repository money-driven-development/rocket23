package com.initcloud.rocket23.checklist.entity.scanHistory;

import com.initcloud.rocket23.common.entity.BaseEntity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "RULE_NAME")
    @NotNull
    private String ruleName;

    @Column(name = "RULE_DESCRIPTION")
    @NotNull
    private String ruleDescription;

    @Column(name = "APP_TYPE")
    @NotNull
    private String appType;

    @Column(name = "SCAN_RESULT")
    @NotNull
    private String scanResult;

    @Column(name = "TARGET_FILE_NAME")
    @NotNull
    private String targetFileName;

    /**
     * 2개의 원소를 가짐. 정수, 문자열 [1, "code"]
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "scanHistoryDetail")
    private List<CodeBlock> codeBlock;

    @Builder
    public ScanHistoryDetail(ScanHistory scanHistory,
                             String ruleName,
                             String ruleDescription,
                             String appType,
                             String scanResult,
                             String targetFileName) {
        this.scanHistory = scanHistory;
        this.ruleName = ruleName;
        this.ruleDescription = ruleDescription;
        this.targetFileName = targetFileName;
        this.appType = appType;
        this.scanResult = scanResult;
        scanHistory.getScanDetails().add(this);
    }
}
