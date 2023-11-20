package com.initcloud.rocket23.checklist.dto;

import java.util.ArrayList;
import java.util.List;
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
}
