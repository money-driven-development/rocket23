package com.initcloud.dockerapi.container.dto;

import com.initcloud.dockerapi.container.enums.IaCType;

import lombok.Getter;

@Getter
public class IaCScanRequestDto {
	private final IaCType iacType; // Terraform, Ansible...
	private final String iacPath;

	public IaCScanRequestDto(IaCType iacType, String iacPath) {
		this.iacType = iacType;
		this.iacPath = iacPath;
	}
}
