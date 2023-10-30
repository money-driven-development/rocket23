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
@Entity(name = "FAILED_EVALUATED_KEYS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FailedEvaluatedKeys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "failed_evaluated_keys_id")
    private Long id;

    @Column(name = "evaluated_keys")
    private String evaluatedKeys;

    @JoinColumn(name = "failed_check_result_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FailedCheckResult failedCheckResult;

    public FailedEvaluatedKeys(String evaluatedKeys) {
        this.evaluatedKeys = evaluatedKeys;
    }

}
