package com.initcloud.dockerapi.container.enums;

import lombok.Getter;

@Getter
public enum ContainerImages {
	ALPINE_LATEST("alpine", "latest"),
	SCANNER_ALPINE_LATEST("floodnut/scanner-alpine", "latest"),
	SCANNER_LATEST("scanner", "latest");

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
