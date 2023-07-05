package com.initcloud.scanhistory.checklist.dto;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import com.initcloud.scanhistory.checklist.entity.ScanHistoryEntity;
import com.initcloud.scanhistory.checklist.repository.ScanHistoryRepository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanHistoryDto {
	private String fileName;
	private String fileHash;
	private Integer passed;
	private Integer skipped;
	private Integer failed;
	private Integer high;
	private Integer medium;
	private Integer low;
	private Integer unknown;
	private Integer cveCount;
	private Double score;

	public ScanHistoryDto(ScanHistoryEntity entity) {
		this.fileName = entity.getFileName();
		this.fileHash = entity.getFileHash();
		this.passed = entity.getPassed();
		this.failed = entity.getFailed();
		this.skipped = entity.getSkipped();
		this.high = entity.getHigh();
		this.medium = entity.getLow();
		this.low = entity.getLow();
		this.unknown = entity.getUnknown();
		this.cveCount = entity.getCveCount();
		this.score = entity.getScore();
	}
}
