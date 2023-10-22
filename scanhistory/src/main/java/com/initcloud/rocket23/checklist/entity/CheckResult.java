package com.initcloud.rocket23.checklist.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity(name = "CHECK_RESULT")
@RequiredArgsConstructor
public class CheckResult {
    @Id
    @Column(name = "check_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String result;

    @Column(name = "evalueted_keys")
    @ElementCollection(targetClass = String.class)
    private List<String> evaluatedKeys;

    public CheckResult(String result, List<String> evaluatedKeys) {
        this.result = result;
        this.evaluatedKeys = evaluatedKeys;
    }
}