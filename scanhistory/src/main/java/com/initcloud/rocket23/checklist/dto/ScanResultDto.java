package com.initcloud.rocket23.checklist.dto;

import com.initcloud.rocket23.checklist.entity.ScanHistory;

import lombok.AccessLevel;
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

	public ScanResultDto(ScanHistory entity) {
		this.fileName = entity.getFileName();
		this.high = entity.getHigh();
		this.medium = entity.getMedium();
		this.low = entity.getLow();
		this.unknown = entity.getUnknown();
		this.score = entity.getScore();
		this.passed = entity.getPassed();
		this.failed = entity.getFailed();
		this.skipped = entity.getSkipped();
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Summary {
		private String fileName;
		private Integer passed;
		private Integer skipped;
		private Integer failed;

		public Summary(ScanHistory entity){
			this.fileName = entity.getFileName();
			this.passed = entity.getPassed();
			this.skipped = entity.getSkipped();
			this.failed = entity.getFailed();
		}
	}
}
