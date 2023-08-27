package com.initcloud.rocket23.project.service;

import com.initcloud.rocket23.common.client.GithubFeignClient;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.redis.pubsub.RedisMessagePublisher;
import com.initcloud.rocket23.project.dto.GithubDto;
import com.initcloud.rocket23.project.dto.RedisFileDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GithubService {

	private final GithubFeignClient githubFeignClient;
	private final RedisMessagePublisher redisMessagePublisher;
	private final Environment env;

	public List<GithubDto.RepositoryInfo> getRepositories(@NonNull String user) {
		return githubFeignClient.getRepositoryList(user);
	}

	public List<GithubDto.Contents> getRepository(String user, String repo, String branch) {
		return githubFeignClient.getRepositoryDetails(user, repo, branch);
	}

	public GithubDto.File getBlobsFromGit(String user, String repo, String hash, String branch) {
		GithubDto.File file = githubFeignClient.getFiles(user, repo, hash, branch);
		String uuid = UUID.randomUUID().toString();
		redisMessagePublisher.publishFileMessage(new RedisFileDto(uuid, repo));
		return file;
	}

	public void saveZipFileFromResponse(ResponseEntity<Resource> responseEntity, Path targetPath, String repo) throws IOException {
		Resource zipResource = responseEntity.getBody();
		String uuid = UUID.randomUUID().toString();

		if (zipResource != null) {
			try (InputStream inputStream = zipResource.getInputStream()) {
				Files.copy(inputStream, targetPath.resolve("downloaded.zip"), StandardCopyOption.REPLACE_EXISTING);
			}
		}

		redisMessagePublisher.publishFileMessage(new RedisFileDto(uuid, repo));
	}

	public void getZip(String user, String repo) {
		ResponseEntity<Resource> responseEntity = githubFeignClient.getZipFiles(user, repo);
		String targetPathStr = env.getProperty("UPLOAD_PATH");

		try {
			Path targetPath = Paths.get(targetPathStr);
			saveZipFileFromResponse(responseEntity, targetPath, repo);

		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

}