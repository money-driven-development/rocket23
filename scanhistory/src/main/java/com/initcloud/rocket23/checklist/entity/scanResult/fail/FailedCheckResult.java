//package com.initcloud.rocket23.checklist.entity.scanResult.fail;
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
//@Entity(name = "FAILED_CHECK_RESULT")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
//public class FailedCheckResult {
//    @Id
//    @Column(name = "failed_check_result_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String result;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "failedCheckResult")
//    private List<FailedEvaluatedKeys> evaluatedKeys;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "failed_checks_id")
//    private FailedChecks failedChecks;
//
//    public FailedCheckResult(String result, List<FailedEvaluatedKeys> evaluatedKeys, FailedChecks failedChecks) {
//        this.result = result;
//        this.evaluatedKeys = evaluatedKeys;
//        this.failedChecks = failedChecks;
//    }
//}