package com.initcloud.rocket23.file.dto;

import com.initcloud.rocket23.file.entity.FileEntity;
import com.initcloud.rocket23.file.enums.ServerType;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDto {
	private Long id;
	private String fileName;
	private String uuid;
	private String path;
	private ServerType serverType;

	@Builder
	public FileDto(Long id, String fileName, String uuid, String path, ServerType serverType) {
		this.id = id;
		this.fileName = fileName;
		this.uuid = uuid;
		this.path = path;
		this.serverType = serverType;
	}

	public static FileDto toDto(String name, String uuid, String path, ServerType serverType) {
		return FileDto.builder()
			.fileName(name)
			.uuid(uuid)
			.path(path)
			.serverType(serverType)
			.build();
	}

	public FileEntity toEntity() {
		return FileEntity.builder()
			.id(id)
			.fileName(fileName)
			.uuid(uuid)
			.path(path)
			.serverType(serverType)
			.build();
	}
}
