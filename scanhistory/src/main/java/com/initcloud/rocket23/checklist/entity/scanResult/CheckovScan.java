package com.initcloud.rocket23.checklist.entity.scanResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "checkov_scan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckovScan {

    @Id
    @Column(name = "checkov_scan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_type")
    @JsonProperty("check_type")
    private String checkType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "results_id")
    private Results results;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "summary_id")
    private Summary summary;

    @Column(name = "url")
    private String url;

    @Column(name = "team_code")
    private String teamCode;

    @Column(name = "project_code")
    private String projectCode;

    public CheckovScan(String checkType,
                       Results results,
                       Summary summary,
                       String url) {
        this.checkType = checkType;
        this.results = results;
        this.summary = summary;
        this.url = url;
    }
}
