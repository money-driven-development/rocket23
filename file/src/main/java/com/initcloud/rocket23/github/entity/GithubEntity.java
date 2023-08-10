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

	@Column(name = "FILE_CONTENTS")
	private String contents;

	@Column(name = "FILE_ENCODING")
	private String encoding;

	@Column(name = "FILE_URL")
	private String url;

	@Column(name = "FILE_SHA1")
	private String sha;
	@Column(name = "FILE_SIZE")
	private Long size;
	@Column(name = "FILE_NODE")
	private String nodeId;

	@Builder
	public GithubEntity(String uuid, String contents, String encoding, String url, String sha, Long size,
		String nodeId) {
		this.uuid = uuid;
		this.contents = contents;
		this.encoding = encoding;
		this.url = url;
		this.sha = sha;
		this.size = size;
		this.nodeId = nodeId;
	}

}
