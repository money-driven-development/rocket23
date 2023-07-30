package com.initcloud.scanhistory.checklist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.initcloud.scanhistory.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "SCAN_HISTORY_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistoryDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "history_seq", updatable = false)
	private ScanHistory historySeq;

	@Column(name = "CHECk_TYPE")
	@NotNull
	private String checkType;

	@Column(name = "TARGET_FILE")
	@NotNull
	private String targetFile;

	@Column(name = "SCAN_RESULT")
	@NotNull
	private String scanResult;

	@Column(name = "LINE")
	@NotNull
	private String line;

	@Column(name = "CODE")
	@NotNull
	private String code;

	@Builder
	public ScanHistoryDetail(ScanHistory scanHistory, String checkType,
		String targetFile, String scanResult, String line, String code) {
		this.historySeq = scanHistory;
		this.checkType = checkType;
		this.targetFile = targetFile;
		this.scanResult = scanResult;
		this.line = line;
		this.code = code;
	}
}
