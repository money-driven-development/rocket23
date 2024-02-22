package com.initcloud.dockerapi.container.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScanSaveRequestDto {

    private final String data;
    private final String teamCode;
    private final String projectCode;
    private final String fileHash;

    @Builder
    public ScanSaveRequestDto(String data, String teamCode, String projectCode, String fileHash) {
        this.data = data;
        this.teamCode = teamCode;
        this.projectCode = projectCode;
        this.fileHash = fileHash;
    }

}
