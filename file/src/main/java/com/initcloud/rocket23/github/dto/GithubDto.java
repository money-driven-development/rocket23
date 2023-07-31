package com.initcloud.rocket23.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GithubDto {

	private static final String GITHUB_API_BASE_URL = "https://api.github.com";

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Permission {
		private Boolean admin;
		private Boolean maintain;
		private Boolean push;
		private Boolean triage;
		private Boolean pull;
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RepositoryInfo {
		private Long id;
		private String nodeId;
		private String fullName;
		private String visibility;
		@JsonProperty("created_at")
		private String createdAt;
		@JsonProperty("updated_at")
		private String updatedAt;
		private String url;
		@JsonProperty("downloads_url")
		private String downloadsUrl;
		@JsonProperty("default_branch")
		private String defaultBranch;
		private Permission permissions;

		@Builder
		public RepositoryInfo(Long id, String nodeId, String fullName, String visibility, String createdAt,
			String updatedAt, String url, String downloadsUrl, String defaultBranch, Permission permissions) {
			this.id = id;
			this.nodeId = nodeId;
			this.fullName = fullName;
			this.visibility = visibility;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.url = url;
			this.downloadsUrl = downloadsUrl;
			this.defaultBranch = defaultBranch;
			this.permissions = permissions;
		}

		public static RepositoryInfo toApiRepository(final RepositoryInfo repo) {
			return RepositoryInfo.builder()
				.id(repo.getId())
				.nodeId(repo.getNodeId())
				.fullName(repo.getFullName())
				.visibility(repo.getVisibility())
				.createdAt(repo.getCreatedAt())
				.updatedAt(repo.getUpdatedAt())
				.url(repo.getUrl().replace(GITHUB_API_BASE_URL, ""))
				.downloadsUrl(repo.getDownloadsUrl().replace(GITHUB_API_BASE_URL, ""))
				.defaultBranch(repo.getDefaultBranch())
				.permissions(repo.getPermissions())
				.build();
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Contents {
		private String type;
		private String name;
		private String sha;
		private String path;
		private String url;

		public Contents(String type, String name, String sha, String path, String url) {
			this.type = type;
			this.name = name;
			this.sha = sha;
			this.path = path;
			this.url = url.replace(GITHUB_API_BASE_URL, "");
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class File {
		private String content;
		private String encoding;
		private String url;
		private String sha;
		private long size;
		private String nodeId;

		public File(String content, String encoding, String url, String sha, long size, String nodeId) {
			this.content = content;
			this.encoding = encoding;
			this.sha = sha;
			this.size = size;
			this.nodeId = nodeId;
			this.url = url.replace(GITHUB_API_BASE_URL, "");
		}
	}
}