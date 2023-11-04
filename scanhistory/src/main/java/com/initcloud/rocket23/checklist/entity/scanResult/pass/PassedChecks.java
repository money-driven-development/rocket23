package com.initcloud.rocket23.checklist.entity.scanResult.pass;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.initcloud.rocket23.checklist.entity.scanResult.Results;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "PASSED_CHECKS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PassedChecks {

    @Id
    @Column(name = "passed_checks_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "results_id")
    private Results results;

    @Column(name = "check_id")
    private String checkId;

    @Column(name = "bc_check_id")
    private String bcCheckId;

    @Column(name = "check_name")
    private String checkName;

    /**
     * 2개의 원소를 가짐. 정수, 문자열 [1, "code"]
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "passedChecks")
    private List<PassedCodeBlock> codeBlock;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "passedChecks")
    private PassedCheckResult checkResult;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_abs_path")
    private String fileAbsPath;

    @Column(name = "repo_file_path")
    private String repoFilePath;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "passedChecks")
    private List<PassedFileLineRange> fileLineRange;

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

    public PassedChecks(Results results, String checkId, String bcCheckId, String checkName,
                        List<PassedCodeBlock> codeBlock,
                        PassedCheckResult checkResult, String filePath, String fileAbsPath, String repoFilePath,
                        List<PassedFileLineRange> fileLineRange, String resource, String evaluations, String checkClass,
                        String fixedDefinition, String entityTags, String callerFilePath, String callerFileLineRange,
                        String resourceAddress, String severity, String bcCategory, String benchmarks,
                        String description,
                        String shortDescription, String vulnerabilityDetails, String connectedNode, String guideline,
                        List details, String checkLen, String definitionContextFilePath) {
        this.results = results;
        this.checkId = checkId;
        this.bcCheckId = bcCheckId;
        this.checkName = checkName;
        this.codeBlock = codeBlock;
        this.checkResult = checkResult;
        this.filePath = filePath;
        this.fileAbsPath = fileAbsPath;
        this.repoFilePath = repoFilePath;
        this.fileLineRange = fileLineRange;
        this.resource = resource;
        this.evaluations = evaluations;
        this.checkClass = checkClass;
        this.fixedDefinition = fixedDefinition;
        this.entityTags = entityTags;
        this.callerFilePath = callerFilePath;
        this.callerFileLineRange = callerFileLineRange;
        this.resourceAddress = resourceAddress;
        this.severity = severity;
        this.bcCategory = bcCategory;
        this.benchmarks = benchmarks;
        this.description = description;
        this.shortDescription = shortDescription;
        this.vulnerabilityDetails = vulnerabilityDetails;
        this.connectedNode = connectedNode;
        this.guideline = guideline;
        this.details = details;
        this.checkLen = checkLen;
        this.definitionContextFilePath = definitionContextFilePath;
    }
}
