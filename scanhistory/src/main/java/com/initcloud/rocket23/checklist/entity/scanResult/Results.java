//package com.initcloud.rocket23.checklist.entity.scanResult;
//
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.fasterxml.jackson.databind.annotation.JsonNaming;
//import com.initcloud.rocket23.checklist.entity.scanResult.fail.FailedChecks;
//import com.initcloud.rocket23.checklist.entity.scanResult.pass.PassedChecks;
//import java.util.List;
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.ElementCollection;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
//public class Results {
//
//    @Id
//    @Column(name = "results_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany(mappedBy = "results", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<PassedChecks> passedChecks;
//
//    @OneToMany(mappedBy = "results", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<FailedChecks> failedChecks;
//
//    @Column(name = "skipped_checks", columnDefinition = "JSON")
//    @ElementCollection(targetClass = String.class)
//    private List<Object> skippedChecks;
//
//    @Column(name = "parsing_errors", columnDefinition = "JSON")
//    @ElementCollection(targetClass = String.class)
//    private List<Object> parsingErrors;
//
//    public Results(List<PassedChecks> passedChecks, List<FailedChecks> failedChecks, List<Object> skippedChecks,
//                   List<Object> parsingErrors) {
//        this.passedChecks = passedChecks;
//        this.failedChecks = failedChecks;
//        this.skippedChecks = skippedChecks;
//        this.parsingErrors = parsingErrors;
//    }
//}