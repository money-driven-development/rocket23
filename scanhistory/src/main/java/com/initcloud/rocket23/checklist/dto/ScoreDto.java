package com.initcloud.rocket23.checklist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScoreDto {

    private Integer successHigh = 0;
    private Integer successMedium = 0;
    private Integer successLow = 0;
    private Integer failHigh = 0;
    private Integer failMedium = 0;
    private Integer failLow = 0;
    private double score = 0;

    @Builder
    public ScoreDto(Integer successHigh, Integer successMedium, Integer successLow, Integer failHigh,
                    Integer failMedium,
                    Integer failLow, double score) {
        this.successHigh = successHigh;
        this.successMedium = successMedium;
        this.successLow = successLow;
        this.failHigh = failHigh;
        this.failMedium = failMedium;
        this.failLow = failLow;
        this.score = score;
    }

    @Builder
    public ScoreDto(ScoreDto dto, double score){
        this.successHigh = dto.getSuccessHigh();
        this.successMedium = dto.getSuccessMedium();
        this.successLow = dto.getSuccessLow();
        this.failHigh = dto.getFailHigh();
        this.failMedium = dto.getFailMedium();
        this.failLow = dto.getFailLow();
        this.score = score;
    }

    public void incrementSuccessHigh() {
        this.successHigh++;
    }

    public void incrementSuccessMedium() {
        this.successMedium++;
    }

    public void incrementSuccessLow() {
        this.successLow++;
    }

    public void incrementFailHigh() {
        this.failHigh++;
    }

    public void incrementFailMedium() {
        this.failMedium++;
    }

    public void incrementFailLow() {
        this.failLow++;
    }

}
