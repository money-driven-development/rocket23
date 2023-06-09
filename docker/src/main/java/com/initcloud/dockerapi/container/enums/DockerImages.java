package com.initcloud.dockerapi.container.enums;

import lombok.Getter;

@Getter
public enum DockerImages {
	ALPINE_LATEST("alpine", "latest");

	private final String repository;
	private final String tag;

	DockerImages(String repository, String tag) {
		this.repository = repository;
		this.tag = tag;
	}

	public static String getFullImageName(DockerImages image) {
		return image.repository + ":" + image.getTag();
	}
}
