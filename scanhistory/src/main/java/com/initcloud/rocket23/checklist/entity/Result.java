package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "RESULT")
@RequiredArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinTable(name = "passed_checks")
    private List<Checks> passedChecks;

    @OneToMany
    @JoinTable(name = "failed_checks")
    private List<Checks> failedChecks;

    @Column(name = "skipped_checks", columnDefinition = "JSON")
    @ElementCollection(targetClass = String.class)
    private List<Object> skippedChecks;

    @Column(name = "parsing_errors", columnDefinition = "JSON")
    @ElementCollection(targetClass = String.class)
    private List<Object> parsingErrors;

    public Result(List<Checks> passedChecks, List<Checks> failedChecks, List<Object> skippedChecks, List<Object> parsingErrors) {
        this.passedChecks = passedChecks;
        this.failedChecks = failedChecks;
        this.skippedChecks = skippedChecks;
        this.parsingErrors = parsingErrors;
    }
}