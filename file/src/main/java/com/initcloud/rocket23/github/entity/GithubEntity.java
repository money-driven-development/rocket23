package com.initcloud.rocket23.github.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.initcloud.rocket23.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "GIT_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GIT_FILE_ID")
	private Long id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "FILE_NAME")
	private String name;

	@Column(name = "FILE_PATH")
	private String path;

	@Column(name = "FILE_URL")
	private String url;

	@Column(name = "FILE_STORAGE_TYPE")
	private String storageType;

	@Column(name = "FILE_SHA1_1")
	private String sha1;

	@Column(name = "FILE_SHA1_2")
	private String sha2;

	@Builder
	public GithubEntity(String uuid, String name, String path, String url, String storageType, String sha1,
		String sha2) {
		this.uuid = uuid;
		this.name = name;
		this.path = path;
		this.url = url;
		this.storageType = storageType;
		this.sha1 = sha1;
		this.sha2 = sha2;
	}

}
