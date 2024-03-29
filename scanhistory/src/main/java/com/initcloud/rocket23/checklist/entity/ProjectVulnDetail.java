package com.initcloud.rocket23.checklist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "PROJECT_VULN_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectVulnDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_VULN_DETAIL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HISTORY_ID")
    private ScanHistory scanHistory;

    @Column(name = "LINE")
    @NotNull
    private String line;

    @Column(name = "IS_VALID")
    @NotNull
    private String isValid;

    @Column(name = "VAILD_CONTENT")
    @NotNull
    private String validContent;

    @Column(name = "VULN_TITLE")
    @NotNull
    private String vulnTitle;

    @Column(name = "HAS_VULN")
    @NotNull
    private String hasVuln;

    @Column(name = "VULN_CONTENT")
    @NotNull
    private String vulnContent;

    @Column(name = "VULN_SCORE")
    @NotNull
    private Integer vulnScore;

    @Builder
    public ProjectVulnDetail(ScanHistory scanHistory, String line, String isValid, String validContent,
                             String vulnTitle, String hasVuln, String vulnContent, Integer vulnScore) {
        this.line = line;
        this.scanHistory = scanHistory;
        this.isValid = isValid;
        this.validContent = validContent;
        this.vulnTitle = vulnTitle;
        this.hasVuln = hasVuln;
        this.vulnContent = vulnContent;
        this.vulnScore = vulnScore;
        scanHistory.getFileDetails().add(this);
    }
}
