package com.initcloud.scanhistory.checklist.dto;

import java.time.LocalDateTime;

import com.initcloud.scanhistory.checklist.entity.ScanHistory;

import lombok.AccessLevel;
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

	public HistoryDto(ScanHistory entity) {
		this.id = entity.getHistorySeq();
		this.fileName = entity.getFileName();
		this.fileHash = entity.getFileHash();
		this.scanDateTime = entity.getCreatedAt();
		this.provider = entity.getCsp();
		this.score = entity.getScore();
	}
}
