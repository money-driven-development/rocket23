package com.initcloud.rocket23.checklist.dto;

import com.initcloud.rocket23.checklist.entity.ScanHistory;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryDto {
	private Long id;
	private String fileName;
	private String fileHash;
	private LocalDateTime scanDateTime;
	private Double score;
	private Integer failed;
	private Integer passed;
	private Integer skipped;

	public HistoryDto(ScanHistory entity) {
		this.id = entity.getId();
		this.fileName = entity.getFileName();
		this.fileHash = entity.getFileHash();
		this.scanDateTime = entity.getCreatedAt();
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
		this.scanDateTime = scanDateTime;
		this.score = score;
		this.failed = failed;
		this.passed = passed;
		this.skipped = skipped;
	}

}