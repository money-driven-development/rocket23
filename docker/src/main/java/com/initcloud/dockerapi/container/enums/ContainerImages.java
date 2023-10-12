package com.initcloud.dockerapi.container.enums;

import lombok.Getter;

@Getter
public enum ContainerImages {
	ALPINE_LATEST("alpine", "latest"), // 컨테이너 구동 테스트 용
	SCANNER_ALPINE_LATEST("floodnut/scanner-alpine", "latest"), // arm64
	SCANNER_LATEST("scanner", "latest"); // x86_64

	private final String repository;
	private final String tag;

	ContainerImages(String repository, String tag) {
		this.repository = repository;
		this.tag = tag;
	}

	public static String getFullImageName(ContainerImages image) {
		return image.repository + ":" + image.getTag();
	}
}
