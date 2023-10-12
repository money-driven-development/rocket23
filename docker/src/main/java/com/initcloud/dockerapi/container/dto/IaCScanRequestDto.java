package com.initcloud.dockerapi.container.dto;

import com.initcloud.dockerapi.container.enums.IaCType;

import lombok.Getter;

@Getter
public class IaCScanRequestDto {
    private final IaCType iacType; // Terraform, Ansible...
    private final String iacPath;

    private final String teamCode;
    private final String projectCode;


    public IaCScanRequestDto(IaCType iacType, String iacPath, String teamCode, String projectCode) {
        this.iacType = iacType;
        this.iacPath = iacPath;
        this.teamCode = teamCode;
        this.projectCode = projectCode;
    }
}
