package com.initcloud.scanhistory.checklist.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.initcloud.scanhistory.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "SCAN_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistoryEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HISTORY_ID")
	private Long id;

	@Column(name = "FILE_NAME")
	@NotNull
	private String fileName;

	@Column(name = "FILE_HASH")
	@NotNull
	private String fileHash;

	@Column(name = "PASSED")
	@NotNull
	private Integer passed;

	@Column(name = "SKIPPED")
	@NotNull
	private Integer skipped;

	@Column(name = "FAILED")
	@NotNull
	private Integer failed;

	@Column(name = "HIGH")
	@NotNull
	private Integer high;

	@Column(name = "MEDIUM")
	@NotNull
	private Integer medium;

	@Column(name = "LOW")
	@NotNull
	private Integer low;

	@Column(name = "UNKNOWN")
	@NotNull
	private Integer unknown;

	@Column(name = "CVE_COUNT")
	@NotNull
	private Integer cveCount;

	@Column(name = "SCORE")
	@NotNull
	private Double score;

	@OneToMany(mappedBy = "scanHistoryEntity")
	private List<ScanHistoryDetailEntity> scanDetails = new ArrayList<>();

	@OneToMany(mappedBy = "scanHistoryEntity")
	private List<FileVulnDetailEntity> fileDetails = new ArrayList<>();

	@Builder
	public ScanHistoryEntity(Long id, String fileName, String fileHash, Integer passed, Integer skipped, Integer failed,
		Integer high,
		Integer medium, Integer low, Integer unknown, Integer cveCount, Double score) {
		this.id = id;
		this.fileName = fileName;
		this.fileHash = fileHash;
		this.passed = passed;
		this.skipped = skipped;
		this.failed = failed;
		this.high = high;
		this.medium = medium;
		this.low = low;
		this.unknown = unknown;
		this.cveCount = cveCount;
		this.score = score;
	}
}
