package com.initcloud.rocket23.checklist.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistoryDetail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanFailDetailDto {
    private int high;
    private int low;
    private int medium;
    private int unknown;
    private List<FailResource> failResourceList = new ArrayList<>();

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class FailResource {
        private String ruleName;
        private String resource;
        private String resourceName;

        private FailResource(String ruleName, String resource, String resourceName) {
            this.ruleName = ruleName;
            this.resource = resource;
            this.resourceName = resourceName;
        }
    }

    public ScanFailDetailDto(ScanHistory scanHistory, List<ScanHistoryDetail> scanHistoryDetails) {
        this.high = scanHistory.getHigh();
        this.low = scanHistory.getLow();
        this.medium = scanHistory.getMedium();
        this.unknown = scanHistory.getUnknown();
        failResourceList.addAll(scanHistoryDetails.stream()
                .map(detail -> new FailResource(detail.getRuleName(), detail.getResource(), detail.getResourceName()))
                .collect(Collectors.toList()));

    }
}
