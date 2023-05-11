package com.initcloud.rocket23.file.dto;

import com.initcloud.rocket23.file.entity.FileEntity;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDto {
    private Long id;
    private String filename;
    private String uuid;
    private String path;
    private String servertype;

    @Builder
    public FileDto(Long id, String filename, String uuid, String path, String servertype) {
        this.id = id;
        this.filename = filename;
        this.uuid = uuid;
        this.path = path;
        this.servertype = servertype;
    }

    public FileEntity toEntity() {
        return FileEntity.builder()
                .id(id)
                .filename(filename)
                .uuid(uuid)
                .path(path)
                .serverType(servertype)
                .build();
    }
}
