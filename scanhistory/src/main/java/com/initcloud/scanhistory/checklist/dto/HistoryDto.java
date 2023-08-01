package com.initcloud.scanhistory.checklist.dto;

import java.time.LocalDateTime;

import com.initcloud.scanhistory.checklist.entity.ScanHistory;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryDto {
	private Long id;
	private String fileName;
	private String fileHash;
	private String provider;
	private LocalDateTime scanDateTime;
	private Double score;
	private Integer failed;
	private Integer passed;
	private Integer skipped;

	public HistoryDto(ScanHistory entity) {
		this.id = entity.getHistoryId();
		this.fileName = entity.getFileName();
		this.fileHash = entity.getFileHash();
		this.scanDateTime = entity.getCreatedAt();
		this.provider = entity.getCsp();
		this.score = entity.getScore();
		this.failed = entity.getFailed();
		this.passed = entity.getPassed();
		this.skipped = entity.getSkipped();
	}

	@Builder
	public HistoryDto(Long id, String fileName, String fileHash, String provider, LocalDateTime scanDateTime,
		Double score, Integer failed, Integer passed, Integer skipped) {
		this.id = id;
		this.fileName = fileName;
		this.fileHash = fileHash;
		this.provider = provider;
		this.scanDateTime = scanDateTime;
		this.score = score;
		this.failed = failed;
		this.passed = passed;
		this.skipped = skipped;
	}

}
