package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CheckResult {
    private String result;
    private List evaluatedKeys;

    public CheckResult(String result, List evaluatedKeys) {
        this.result = result;
        this.evaluatedKeys = evaluatedKeys;
    }
}