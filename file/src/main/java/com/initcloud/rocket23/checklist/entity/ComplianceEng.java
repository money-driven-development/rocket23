package com.initcloud.rocket23.checklist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.initcloud.rocket23.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMPLIANCE")
public class ComplianceEng extends BaseEntity {

	@Id
	@Column(name = "COMP_ID", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long compId;

	@ManyToOne
	@JoinColumn(name = "rule_id", updatable = false)
	private CustomRule rule;

	@Column(name = "COMPLIANCE_NAME")
	@NotNull
	@Size(max = 16)
	private String complianceName;

	@Column(name = "COMPLIANCE_NUMBER")
	@NotNull
	@Size(max = 8)
	private String complianceNumber;

	@Column(name = "CATEGORY")
	@NotNull
	@Size(max = 128)
	private String category;

	@Column(name = "ARTICLE")
	@NotNull
	@Size(max = 128)
	private String article;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DETAIL")
	private String detail;

	@Builder(builderClassName = "complianceEnglishBuilder", builderMethodName = "complianceEnglishBuilder")
	public ComplianceEng(CustomRule rule, String complianceName, String complianceNumber, String category,
		String article, String description, String detail) {
		this.rule = rule;
		this.complianceName = complianceName;
		this.complianceNumber = complianceNumber;
		this.category = category;
		this.article = article;
		this.description = description;
		this.detail = detail;
	}
}
