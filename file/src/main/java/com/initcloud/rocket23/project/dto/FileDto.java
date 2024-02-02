package com.initcloud.rocket23.project.dto;

import com.initcloud.rocket23.project.enums.ProjectType;

import java.nio.file.Path;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDto {
	private String fileName;
	private Path path;
	private ProjectType projectType;
	private String code;

	@Builder
	public FileDto(String fileName, Path path, ProjectType projectType, String code) {
		this.fileName = fileName;
		this.path = path;
		this.projectType = projectType;
		this.code = code;
	}

	public static FileDto toDto(String name, Path path, ProjectType projectType, String code) {
		return FileDto.builder()
			.fileName(name)
			.path(path)
			.projectType(projectType)
			.code(code)
			.build();
	}
}
