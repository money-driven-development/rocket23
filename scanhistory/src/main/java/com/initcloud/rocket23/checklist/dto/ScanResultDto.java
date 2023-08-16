package com.initcloud.rocket23.checklist.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.initcloud.rocket23.checklist.entity.ScanHistory;
import com.initcloud.rocket23.checklist.entity.ScanHistoryDetail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanResultDto {
	private String fileName;
	private Integer high;
	private Integer medium;
	private Integer low;
	private Integer unknown;
	private Double score;
	private Integer passed;
	private Integer failed;
	private Integer skipped;
	private LocalDateTime created;
	private List<Detail> scanResultDetailList = new ArrayList<>();

	@Builder
	public ScanResultDto(ScanHistory scanHistory, List<ScanHistoryDetail> scanHistoryDetails) {
		this.fileName = scanHistory.getFileName();
		this.high = scanHistory.getHigh();
		this.medium = scanHistory.getMedium();
		this.low = scanHistory.getLow();
		this.unknown = scanHistory.getUnknown();
		this.score = scanHistory.getScore();
		this.passed = scanHistory.getPassed();
		this.failed = scanHistory.getFailed();
		this.skipped = scanHistory.getSkipped();
		this.created = scanHistory.getCreatedAt();
		//todo 좀더 이쁘게 dto를 처리하는 방법...
		if (scanHistoryDetails != null) {
			this.scanResultDetailList.addAll(scanHistoryDetails.stream()
				.map(Detail::new)
				.collect(Collectors.toList()));
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Detail {
		private String checkType;
		private String appType;
		private String scanResult;
		private String csp;
		private String line;
		private String code;
		private String ruleName;
		private String resource;
		private String resourceName;

		public Detail(ScanHistoryDetail scanHistoryDetail) {
			this.checkType = scanHistoryDetail.getCheckType();
			this.appType = scanHistoryDetail.getAppType();
			this.scanResult = scanHistoryDetail.getScanResult();
			this.csp = scanHistoryDetail.getCsp();
			this.line = scanHistoryDetail.getLine();
			this.code = scanHistoryDetail.getCode();
			this.ruleName = scanHistoryDetail.getRuleName();
			this.resource = scanHistoryDetail.getResource();
			this.resourceName = scanHistoryDetail.getResourceName();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Summary {
		private String fileName;
		private Integer passed;
		private Integer skipped;
		private Integer failed;

		public Summary(ScanHistory entity) {
			this.fileName = entity.getFileName();
			this.passed = entity.getPassed();
			this.skipped = entity.getSkipped();
			this.failed = entity.getFailed();
		}
	}
}
