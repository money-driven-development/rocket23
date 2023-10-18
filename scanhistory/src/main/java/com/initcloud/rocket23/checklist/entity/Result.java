package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Result {

    private List<Checks> passedChecks;

    private List<Checks> failedChecks;

    private List<Object> skippedChecks;

    private List<Object> parsingErrors;

    public Result(List<Checks> passedChecks, List<Checks> failedChecks, List<Object> skippedChecks, List<Object> parsingErrors) {
        this.passedChecks = passedChecks;
        this.failedChecks = failedChecks;
        this.skippedChecks = skippedChecks;
        this.parsingErrors = parsingErrors;
    }
}