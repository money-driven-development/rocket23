package com.initcloud.rocket23.checklist.entity.scanResult.fail;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "FAILED_FILE_LINE_RANGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FailedFileLineRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "failed_file_line_range_id")
    private Long id;

    @Column(name = "line_range")
    private Integer lineRange;

    @JoinColumn(name = "failed_checks_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FailedChecks failedChecks;

    public FailedFileLineRange(Integer lineRange) {
        this.lineRange = lineRange;
    }

}
