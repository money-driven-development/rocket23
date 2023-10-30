package com.initcloud.rocket23.checklist.entity.scanHistory;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "SCAN_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HISTORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private TeamProject project;

    @Column(name = "PROJECT_NAME")
    @NotNull
    private String projectName;

    @Column(name = "PROJECT_Code")
    @NotNull
    private String projectCode;

    @Column(name = "PROJECT_HASH")
    @NotNull
    private String projectHash;

    @Column(name = "USERNAME")
    @NotNull
    private String username;

    @Column(name = "PASSED")
    @NotNull
    private Integer passed;

    @Column(name = "SKIPPED")
    @NotNull
    private Integer skipped;

    @Column(name = "FAILED")
    @NotNull
    private Integer failed;

    @Column(name = "HIGH")
    @NotNull
    private Integer high;

    @Column(name = "MEDIUM")
    @NotNull
    private Integer medium;

    @Column(name = "LOW")
    @NotNull
    private Integer low;

    @Column(name = "UNKNOWN")
    @NotNull
    private Integer unknown;

    @Column(name = "CVE_COUNT")
    @NotNull
    private Integer cveCount;

    @Column(name = "SCORE")
    @NotNull
    private Double score;

    @OneToMany(mappedBy = "scanHistory")
    private List<ScanHistoryDetail> scanDetails = new ArrayList<>();

    @OneToMany(mappedBy = "scanHistory")
    private List<ProjectVulnDetail> fileDetails = new ArrayList<>();

    @Deprecated
    @Column(name = "FILE_NAME")
    private String fileName = "will_be_deprecated_file_name";

    @Deprecated
    @Column(name = "FILE_HASH")
    private String fileHash = "will_be_deprecated_file_hash";

    @Builder
    public ScanHistory(Long id, Team team, TeamProject project, String projectName, String projectCode,
                       String projectHash, Integer passed,
                       Integer skipped, Integer failed, Integer high, Integer medium, Integer low, Integer unknown,
                       Integer cveCount,
                       Double score) {
        this.id = id;
        this.team = team;
        this.project = project;
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.projectHash = projectHash;
        this.passed = passed;
        this.skipped = skipped;
        this.failed = failed;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.unknown = unknown;
        this.cveCount = cveCount;
        this.score = score;
    }
}