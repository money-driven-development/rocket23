package com.initcloud.rocket23.checklist.dto;

import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistoryDetail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private LocalDateTime created_at;
	private List<Detail> scanResultDetailList = new ArrayList<>();

	@Builder
	public ScanResultDto(ScanHistory scanHistory, List<ScanHistoryDetail> scanHistoryDetails) {
		this.fileName = scanHistory.getProjectName();
		this.high = scanHistory.getHigh();
		this.medium = scanHistory.getMedium();
		this.low = scanHistory.getLow();
		this.unknown = scanHistory.getUnknown();
		this.score = scanHistory.getScore();
		this.passed = scanHistory.getPassed();
		this.failed = scanHistory.getFailed();
		this.skipped = scanHistory.getSkipped();
		this.created_at = scanHistory.getCreatedAt();
		if (scanHistoryDetails != null) {
			this.scanResultDetailList.addAll(scanHistoryDetails.stream()
				.map(Detail::new)
				.collect(Collectors.toList()));
		}
	}

	@Builder(builderClassName = "demoBuilder", builderMethodName = "scanResultDemoBuilder")
	public ScanResultDto(String fileName, Integer high, Integer medium, Integer low, Integer unknown, Double score,
		Integer passed, Integer failed, Integer skipped, LocalDateTime created, List<Detail> scanResultDetailList) {
		this.fileName = fileName;
		this.high = high;
		this.medium = medium;
		this.low = low;
		this.unknown = unknown;
		this.score = score;
		this.passed = passed;
		this.failed = failed;
		this.skipped = skipped;
		this.created_at = created;
		this.scanResultDetailList = scanResultDetailList;
	}

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Detail {
        private String checkType;
        private String appType;
        private String scanResult;
        private String line;
        private String code;
        private String ruleName;
        private String resource;
        private String resourceName;

        public Detail(ScanHistoryDetail scanHistoryDetail) {
            this.checkType = scanHistoryDetail.getCheckType();
            this.appType = scanHistoryDetail.getAppType();
            this.scanResult = scanHistoryDetail.getScanResult();
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
		private Double score;
		private LocalDateTime created_at;

		public Summary(ScanHistory entity) {
			this.fileName = entity.getProjectName();
			this.passed = entity.getPassed();
			this.skipped = entity.getSkipped();
			this.failed = entity.getFailed();
			this.score = entity.getScore();
			this.created_at = entity.getCreatedAt();
		}
	}
}
