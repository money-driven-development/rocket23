package com.initcloud.rocket23.checklist.dto;

import java.util.ArrayList;
import java.util.List;

import com.initcloud.rocket23.checklist.entity.ScanHistory;
import com.initcloud.rocket23.checklist.entity.ScanHistoryDetail;

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
	private List<FailResource> failResourceList;

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
		this.failResourceList = new ArrayList<>();
		for (ScanHistoryDetail detail : scanHistoryDetails) {
			failResourceList.add(
				new FailResource(detail.getRuleName(), detail.getResource(), detail.getResourceName()));
		}

	}
}
