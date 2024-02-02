package com.initcloud.rocket23.project.dto;

import com.initcloud.rocket23.project.enums.ServerType;

import java.nio.file.Path;
import java.util.List;
import lombok.*;
import org.bouncycastle.cms.PasswordRecipientId;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDto {
	private Long id;
	private String fileName;
	private Path path;
	private ServerType serverType;
	private List<String> code;

	@Builder
	public FileDto(Long id, String fileName, Path path, ServerType serverType, List<String> code) {
		this.id = id;
		this.fileName = fileName;
		this.path = path;
		this.serverType = serverType;
		this.code = code;
	}

	public static FileDto toDto(String name, Path path, ServerType serverType, List<String> code) {
		return FileDto.builder()
			.fileName(name)
			.path(path)
			.serverType(serverType)
			.code(code)
			.build();
	}
}
