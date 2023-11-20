//package com.initcloud.rocket23.checklist.entity.scanResult.pass;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import java.util.List;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Entity(name = "PASSED_CHECK_RESULT")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
//public class PassedCheckResult {
//    @Id
//    @Column(name = "passed_check_result_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String result;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "passedCheckResult")
//    private List<PassedEvaluatedKeys> evaluatedKeys;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "passed_checks_id")
//    private PassedChecks passedChecks;
//
//    public PassedCheckResult(String result, List<PassedEvaluatedKeys> evaluatedKeys, PassedChecks passedChecks) {
//        this.result = result;
//        this.evaluatedKeys = evaluatedKeys;
//        this.passedChecks = passedChecks;
//    }
//}