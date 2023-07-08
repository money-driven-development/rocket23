package com.initcloud.scanhistory.checklist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "FILE_VULN_DETAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileVulnDetailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILEVULNDETAIL_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "HISTORY_ID")
	private ScanHistoryEntity scanHistoryEntity;

	@Column(name = "line")
	@NotNull
	private String line;

	@Column(name = "IS_VALID")
	@NotNull
	private String isValid;

	@Column(name = "VAILD_CONTENT")
	@NotNull
	private String validContent;

	@Column(name = "VULN_TITLE")
	@NotNull
	private String vulnTitle;

	@Column(name = "HAS_VULN")
	@NotNull
	private String hasVuln;

	@Column(name = "VULN_CONTENT")
	@NotNull
	private String vulnContent;

	@Column(name = "VULN_SCORE")
	@NotNull
	private String vulnScores;

	@Builder
	public FileVulnDetailEntity(ScanHistoryEntity scanHistoryEntity, String isValid, String validContent,
		String vulnTitle, String hasVuln, String vulnContent, String vulnScores) {
		this.scanHistoryEntity=scanHistoryEntity;
		this.isValid=isValid;
		this.validContent=validContent;
		this.vulnTitle=vulnTitle;
		this.hasVuln=hasVuln;
		this.vulnContent=vulnContent;
		this.vulnScores=vulnScores;
		scanHistoryEntity.getFileDetails().add(this);
	}
}
