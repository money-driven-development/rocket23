package com.initcloud.dockerapi.container.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum IaCType {

	TERRAFORM("Terraform"),
	ANSIBLE("Ansible"),
	K8S("K8s"),
	DOCKERFILE("Dockerfile");

	@Getter
	private String type;

	IaCType(String type) {
		this.type = type;
	}
}
