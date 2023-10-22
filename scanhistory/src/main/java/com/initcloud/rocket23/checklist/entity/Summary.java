package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int passed;

    private int failed;

    private int skipped;

    @Column(name = "parsing_errors")
    private int parsingErrors;

    @Column(name = "resource_count")
    private int resourceCount;

    @Column(name = "checkov_version")
    private String checkovVersion;

    public Summary(int passed, int failed, int skipped, int parsingErrors, int resourceCount, String checkovVersion) {
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
        this.parsingErrors = parsingErrors;
        this.resourceCount = resourceCount;
        this.checkovVersion = checkovVersion;
    }
}