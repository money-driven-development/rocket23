package com.initcloud.dockerapi.container.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Registry {
		private String dockerHost;
		private boolean dockerTlsVerify;
		private String dockerCertPath;
		private String dockerConfig;
		private String apiVersion;
		private String registryUrl;
		private User registryUser;

		public Registry(String dockerHost, boolean dockerTlsVerify, String dockerCertPath, String dockerConfig,
			String apiVersion, String registryUrl, User registryUser) {
			this.dockerHost = dockerHost;
			this.dockerTlsVerify = dockerTlsVerify;
			this.dockerCertPath = dockerCertPath;
			this.dockerConfig = dockerConfig;
			this.apiVersion = apiVersion;
			this.registryUrl = registryUrl;
			this.registryUser = registryUser;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class User {
		private String registryUsername;
		private String registryEmail;
		private String registryPassword;

		public User(String registryUsername, String registryEmail, String registryPassword) {
			this.registryUsername = registryUsername;
			this.registryEmail = registryEmail;
			this.registryPassword = registryPassword;
		}
	}
}
