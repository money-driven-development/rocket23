package com.initcloud.rocket23.github.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.initcloud.rocket23.common.client.GithubFeignClient;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.file.dto.RedisFileDto;
import com.initcloud.rocket23.github.dto.GithubDto;
import com.initcloud.rocket23.github.entity.GithubEntity;
import com.initcloud.rocket23.github.repository.GithubRepository;
import com.initcloud.rocket23.redis.pubsub.RedisMessagePublisher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubService {

	private final GithubFeignClient githubFeignClient;
	private final RedisMessagePublisher redisMessagePublisher;
	private final GithubRepository githubRepository;
	private final Environment env;

	public List<GithubDto.RepositoryInfo> getRepositories(@NonNull String user) {
		return githubFeignClient.getRepositoryList(user);
	}

	public List<GithubDto.Contents> getRepository(String user, String repo, String branch) {
		return githubFeignClient.getRepositoryDetails(user, repo, branch);
	}

	public List<Object> getCommits(String user, String repo, String branch) {
		return githubFeignClient.getCommitList(user, repo, branch);
	}

	public Object getCommit(String user, String repo, String hash, String branch) {
		return githubFeignClient.getCommitDetails(user, repo, hash, branch);
	}

	public GithubDto.File getBlobsFromGit(String user, String repo, String hash, String branch) {
		GithubDto.File file = githubFeignClient.getFiles(user, repo, hash, branch);

		GithubEntity githubEntity = GithubDto.File.convertToEntity(file);

		redisMessagePublisher.publishFileMessage(RedisFileDto.toDto(githubEntity.getUuid()));
		githubRepository.save(githubEntity);

		return file;
	}

	public void saveZipFileFromResponse(ResponseEntity<Resource> responseEntity, Path targetPath) throws IOException {
		Resource zipResource = responseEntity.getBody();

		if (zipResource != null) {
			try (InputStream inputStream = zipResource.getInputStream()) {
				Files.copy(inputStream, targetPath.resolve("downloaded.zip"), StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	public void getZip(String user, String repo) {
		ResponseEntity<Resource> responseEntity = githubFeignClient.getZipFiles(user, repo);
		String targetPathStr = env.getProperty("CLIENT_PATH");

		try {
			Path targetPath = Paths.get(targetPathStr);
			saveZipFileFromResponse(responseEntity, targetPath);

		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

}