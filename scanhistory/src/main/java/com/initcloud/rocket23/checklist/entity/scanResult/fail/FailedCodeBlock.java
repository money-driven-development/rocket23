package com.initcloud.rocket23.checklist.entity.scanResult.fail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "FAILED_CODE_BLOCK")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FailedCodeBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "failed_code_block_id")
    private Long id;

    private Integer line;

    private String content;

    @JoinColumn(name = "failed_checks_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FailedChecks failedChecks;

    public FailedCodeBlock(Integer line, String content) {
        this.line = line;
        this.content = content;
    }
}
