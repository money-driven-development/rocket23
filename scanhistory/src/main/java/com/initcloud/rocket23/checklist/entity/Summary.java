package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Summary {

    private int passed;
    private int failed;
    private int skipped;
    private int parsingErrors;
    private int resourceCount;
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