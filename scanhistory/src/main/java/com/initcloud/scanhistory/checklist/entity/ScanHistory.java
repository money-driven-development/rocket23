package com.initcloud.scanhistory.checklist.entity;

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
import javax.validation.constraints.Size;

import com.initcloud.scanhistory.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "SCAN_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_seq")
	private Long historySeq;

	@OneToMany(mappedBy = "historySeq")
	private List<ScanHistoryDetail> scanDetails = new ArrayList<>();

	@OneToMany(mappedBy = "scanHistoryEntity")
	private List<FileVulnDetail> fileDetails = new ArrayList<>();

	@Column(name = "team_id")
	private Long teamId;

	@Column(name = "FILE_NAME")
	@NotNull
	private String fileName;

	@Column(name = "FILE_HASH", updatable = false)
	@NotNull
	private String fileHash;

	@Column(name = "CSP")
	@NotNull
	@Size(max = 16)
	private String csp;

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

	@Builder
	public ScanHistory(Long historySeq, String fileName, String fileHash, String csp, Integer passed, Integer skipped,
		Integer failed,
		Integer high,
		Integer medium, Integer low, Integer unknown, Integer cveCount, Double score) {
		this.historySeq = historySeq;
		this.fileName = fileName;
		this.fileHash = fileHash;
		this.csp = csp;
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
