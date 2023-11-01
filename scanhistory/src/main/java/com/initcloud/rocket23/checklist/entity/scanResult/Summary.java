package com.initcloud.rocket23.checklist.entity.scanResult;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Summary {

    @Id
    @Column(name = "summary_id")
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