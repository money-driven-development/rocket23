package com.initcloud.rocket23.file.entity;

import com.initcloud.rocket23.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "FILELIST")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "HASH_512")
    private String hash512;

    @Column(name = "Hash_256")
    private String hash256;

    @Column(name = "PATH")
    private String path;

    @Column(name = "SERVER_TYPE")
    private String serverPath;


}
