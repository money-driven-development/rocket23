package com.initcloud.rocket23.checklist.entity.scanHistory;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.common.utils.UniqueUtils;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "SCAN_HASH")
    @NotNull
    private String scanHash;

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
    private Integer high = 0;

    @Column(name = "MEDIUM")
    @NotNull
    private Integer medium = 0;

    @Column(name = "LOW")
    @NotNull
    private Integer low = 0;

    @Column(name = "UNKNOWN")
    @NotNull
    private Integer unknown = 0;

    @Column(name = "SCORE")
    @NotNull
    private Double score = 0.0;

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
    public ScanHistory(
            Team team, TeamProject project, String projectName, String projectCode,
            String username,
            Integer passed, Integer skipped, Integer failed, Integer high,
            Integer medium, Integer low, Integer unknown,
            Double score) {
        this.team = team;
        this.project = project;
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.scanHash = UniqueUtils.getUUID();
        this.username = username;
        this.passed = passed;
        this.skipped = skipped;
        this.failed = failed;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.unknown = unknown;
        this.score = score;
    }
}