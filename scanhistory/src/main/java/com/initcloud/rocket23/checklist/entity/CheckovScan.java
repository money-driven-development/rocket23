package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * TODO: 엔티티 테이블 추가 후 어노테이션 및 테이블 컬럼 명 수정 필수 !!
 */
@Getter
@RequiredArgsConstructor
public class CheckovScan {

    private String checkType;
    private Result results;
    private int passed;
    private int failed;
    private int skipped;
    private int parsingErrors;
    private int resourceCount;
    private String checkovVersion;
    private Summary summary;
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