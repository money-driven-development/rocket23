package com.initcloud.rocket23.checklist.entity.scanHistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "CODE_BLOCK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_block_id")
    private Long id;

    private Integer line;

    private String content;

    @JoinColumn(name = "scan_history_detail_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ScanHistoryDetail scanHistoryDetail;

    @Builder
    public CodeBlock(Integer line, String content, ScanHistoryDetail scanHistoryDetail) {
        this.line = line;
        this.content = content;
        this.scanHistoryDetail = scanHistoryDetail;
    }
}
