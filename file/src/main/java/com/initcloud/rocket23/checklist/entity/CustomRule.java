package com.initcloud.rocket23.checklist.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.history.entity.ScanHistoryDetail;
import com.initcloud.rocket23.scan.enums.Provider;

import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CUSTOM_RULE")
public class CustomRule extends BaseEntity {
	@Id
	@Column(name = "RULE_id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DEFAULT_RULE_NAME", updatable = false, unique = true, length = 64)
	@NotNull
	private String defaultRuleName;

	@Column(name = "PROVIDER")
	@Enumerated(EnumType.STRING)
	@NotNull
	private Provider provider;

	@Column(name = "RULE_TYPE")
	@NotNull
	@Size(max = 16)
	private String ruleType;

	@Column(name = "SEVERITY")
	@NotNull
	private String level;

	@Column(name = "IS_MODIFIABLE")
	@NotNull
	private Character isModifiable;

	@Column(name = "INSECURE_EXAMPLE")
	private String insecureExample;

	@Column(name = "SECURE_EXAMPLE")
	private String secureExample;

	@Column(name = "CODE")
	private String code;

	@Column(name = "CUSTOM_DEFAULT", updatable = false)
	private String customDefault;

	@OneToMany(mappedBy = "rule")
	private List<CustomRuleDetails> ruleDetails = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<Tag> tags = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<ComplianceEng> complianceEngs = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<ComplianceKor> complianceKors = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<ScanHistoryDetail> historyDetails = new ArrayList<>();
}
