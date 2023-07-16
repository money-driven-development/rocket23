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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "SCAN_HISTORY_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistoryDetailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCANHISTORYDETAIL_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HiSTORY_ID")
	private ScanHistoryEntity scanHistoryEntity;

	@Column(name = "CHECk_TYPE")
	@NotNull
	private String checkType;

	@Column(name = "TARGET_FILE_NAME")
	@NotNull
	private String targetFileName;

	@Column(name = "APP_TYPE")
	@NotNull
	private String appType;

	@Column(name = "PROVIDER")
	@NotNull
	private String provider;

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
	public ScanHistoryDetailEntity(ScanHistoryEntity scanHistoryEntity, String checkType,
		String targetFileName, String appType, String provider, String scanResult, String line, String code) {
		this.scanHistoryEntity = scanHistoryEntity;
		this.checkType = checkType;
		this.targetFileName = targetFileName;
		this.appType = appType;
		this.provider = provider;
		this.scanResult = scanResult;
		this.line = line;
		this.code = code;
		scanHistoryEntity.getScanDetails().add(this);
	}
}