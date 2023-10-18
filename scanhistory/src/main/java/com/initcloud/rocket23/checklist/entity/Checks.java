package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Checks {

    private String checkId;

    private String bcCheckId;

    private String checkName;

    /**
     * 2개의 원소를 가짐.
     * 정수, 문자열
     * [1, "code"]
     */
    private List<List<Object>> codeBlock;

    private CheckResult checkResult;

    private String filePath;

    private String fileAbsPath;

    private String repoFilePath;

    private List<Integer> fileLineRange;

    private String resource;

    private String evaluations;

    private String checkClass;

    private String FixedDefinition;

    private String EntityTags;

    private String CallerFilePath;

    private String CallerFileLineRange;

    private String ResourceAddress;

    private String severity;

    private String BcCategory;

    private String benchmarks;

    private String description;

    private String ShortDescription;

    private String vulnerabilityDetails;

    private String connectedNode;

    private String guideline;

    private List details;

    private String CheckLen;

    private String DefinitionContextFilePath;

}