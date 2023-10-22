package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
public class CheckovScan {

    @Id
    @Column(name = "check_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_type")
    private String checkType;

    @OneToOne
    @JoinColumn(name = "results_id")
    private Result results;

    private int passed;

    private int failed;

    private int skipped;

    @Column(name = "parsing_errors")
    private int parsingErrors;

    @Column(name = "resource_count")
    private int resourceCount;

    @Column(name = "checkov_version")
    private String checkovVersion;

    @OneToOne
    @JoinColumn(name = "summary_id")
    private Summary summary;

    @Column(name = "url")
    private String url;

    public CheckovScan(String checkType,
                       Result results,
                       int passed,
                       int failed,
                       int skipped,
                       int parsingErrors,
                       int resourceCount,
                       String checkovVersion,
                       Summary summary,
                       String url) {
        this.checkType = checkType;
        this.results = results;
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
        this.parsingErrors = parsingErrors;
        this.resourceCount = resourceCount;
        this.checkovVersion = checkovVersion;
        this.summary = summary;
        this.url = url;
    }
}
