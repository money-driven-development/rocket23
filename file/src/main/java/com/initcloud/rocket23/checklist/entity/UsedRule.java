package com.initcloud.rocket23.checklist.entity;

import java.time.LocalDateTime;

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

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "USED_RULE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsedRule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USED_RULE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RULE_ID")
	private CustomRule originRule;

	@Column(name = "RULE_NAME")
	@NotNull
	private String ruleName;

	@Column(name = "IS_MODIFIED")
	@NotNull
	private Character isModified;

	@Column(name = "RULE_ONOFF", columnDefinition = "char(1) default 'y'")
	@NotNull
	private Character isOn;

	@Column(name = "CUSTOM_DETAIL")
	private String customDetail;

	@Builder
	public UsedRule(LocalDateTime createdAt, LocalDateTime modifiedAt, Long id, User user,
		CustomRule originRule, String ruleName, Character isModified, Character isOn, String customDetail) {
		super(createdAt, modifiedAt);
		this.id = id;
		this.user = user;
		this.originRule = originRule;
		this.ruleName = ruleName;
		this.isModified = isModified;
		this.isOn = isOn;
		this.customDetail = customDetail;
	}

}