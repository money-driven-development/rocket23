package com.initcloud.dockerapi.common.enums;

import lombok.Getter;

@Getter
public enum ScanType {

    TERRAFORM("terraform");

    private String type;

    ScanType(String type) {
        this.type = type;
    }
}
