//package com.initcloud.rocket23.checklist.entity.scanResult.pass;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Entity(name = "PASSED_CODE_BLOCK")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PassedCodeBlock {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "passed_code_block_id")
//    private Long id;
//
//    private Integer line;
//
//    private String content;
//
//    @JoinColumn(name = "passed_checks_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private PassedChecks passedChecks;
//
//    public PassedCodeBlock(Integer line, String content) {
//        this.line = line;
//        this.content = content;
//    }
//}
