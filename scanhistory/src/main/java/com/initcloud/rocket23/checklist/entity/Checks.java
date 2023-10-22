package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "CHECKS")
@RequiredArgsConstructor
public class Checks {

    @Id
    @Column(name = "checks_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_id")
    private String checkId;

    @Column(name = "bc_check_id")
    private String bcCheckId;

    @Column(name = "check_name")
    private String checkName;

    /**
     * 2개의 원소를 가짐.
     * 정수, 문자열
     * [1, "code"]
     */
    @Column(name = "code_block")
    @ElementCollection(targetClass = String.class)
    private List<List<Object>> codeBlock;

    @OneToOne
    @JoinTable(name = "check_result")
    private CheckResult checkResult;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_abs_path")
    private String fileAbsPath;

    @Column(name = "repo_file_path")
    private String repoFilePath;

    @Column(name = "file_line_range")
    @ElementCollection(targetClass = String.class)
    private List<Integer> fileLineRange;

    @Column
    private String resource;

    @Column
    private String evaluations;

    @Column(name = "check_class")
    private String checkClass;

    @Column(name = "fixed_definition")
    private String fixedDefinition;

    @Column(name = "entity_tags")
    private String entityTags;

    @Column(name = "caller_file_path")
    private String callerFilePath;

    @Column(name = "caller_file_line_range")
    private String callerFileLineRange;

    @Column(name = "resource_address")
    private String resourceAddress;

    @Column
    private String severity;

    @Column(name = "bc_category")
    private String bcCategory;

    @Column
    private String benchmarks;

    @Column
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "vulnerability_details")
    private String vulnerabilityDetails;

    @Column(name = "connected_node")
    private String connectedNode;

    @Column
    private String guideline;

    @Column
    @ElementCollection(targetClass = String.class)
    private List details;

    @Column(name = "check_len")
    private String checkLen;

    @Column(name = "definition_context_file_path")
    private String definitionContextFilePath;
}
