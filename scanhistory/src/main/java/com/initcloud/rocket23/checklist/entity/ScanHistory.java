package com.initcloud.rocket23.checklist.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "SCAN_HISTORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HISTORY_ID")
	private Long id;

	//todo 23.08.07 teamid 외래키 연결로 변경할 예정입니다.
	@Column(name = "TEAM_ID")
	private Long teamId;

	//todo 23.08.07 projectid 외래키 연결로 변경할 예정입니다.
	@Column(name = "PROJECT_ID")
	private Long projectId;

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

	@Column(name = "CSP")
	@NotNull
	@Size(max = 16)
	private String csp;

	@OneToMany(mappedBy = "scanHistory")
	private List<ScanHistoryDetail> scanDetails = new ArrayList<>();

	@OneToMany(mappedBy = "scanHistory")
	private List<ProjectVulnDetail> fileDetails = new ArrayList<>();

	@Builder
	public ScanHistory(Long id, Long teamId, Long projectId, String fileName, String fileHash, Integer passed,
		Integer skipped, Integer failed,
		Integer high,
		Integer medium, Integer low, Integer unknown, Integer cveCount, Double score) {
		this.id = id;
		this.teamId = teamId;
		this.projectId = projectId;
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