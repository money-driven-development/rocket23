package com.initcloud.rocket23.file.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "FILELIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    @Column(name = "FILENAME")
    @NotNull
    private String filename;

    @Column(name = "UUID")
    @NotNull
    private String uuid;

    @Column(name = "PATH")
    @NotNull
    private String path;

    @Column(name = "SERVER_TYPE")
    private String serverType;

    @Builder
    public FileEntity(Long id, String filename, String uuid, String path, String serverType) {
        this.id = id;
        this.filename = filename;
        this.uuid = uuid;
        this.path = path;
        this.serverType = serverType;
    }


}
