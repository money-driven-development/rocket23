package com.initcloud.rocket23.file.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import com.initcloud.rocket23.file.enums.ServerType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "FILE_LIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_ID")
	private Long id;

	@Column(name = "FILE_NAME")
	@NotNull
	private String fileName;

	@Column(name = "UUID")
	@NotNull
	private String uuid;

	@Column(name = "PATH")
	@NotNull
	private String path;

	@Column(name = "SERVER_TYPE")
	@Enumerated(EnumType.STRING)
	private ServerType serverType;

	@Builder
	public FileEntity(Long id, String fileName, String uuid, String path, ServerType serverType) {
		this.id = id;
		this.fileName = fileName;
		this.uuid = uuid;
		this.path = path;
		this.serverType = serverType;
	}

	public FileEntity(String fileName, String uuid, String path, ServerType serverType) {
		this.fileName = fileName;
		this.uuid = uuid;
		this.path = path;
		this.serverType = serverType;
	}
}
