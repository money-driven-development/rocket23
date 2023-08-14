package com.initcloud.rocket23.github.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private Environment env;

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

		String uuid = UUID.randomUUID().toString();
		GithubEntity githubEntity = GithubEntity.builder()
			.uuid(uuid)
			.url(file.getUrl())
			.build();

		redisMessagePublisher.publishFileMessage(RedisFileDto.toDto(uuid));
		githubRepository.save(githubEntity);

		return file;
	}

	public void saveZipFileFromResponse(ResponseEntity<Resource> responseEntity, Path targetPath) throws IOException {
		Resource zipResource = responseEntity.getBody();

		if (zipResource != null) {
			try (InputStream inputStream = zipResource.getInputStream()) {
				// ZIP 파일 내용을 읽어와서 파일로 저장
				Files.copy(inputStream, targetPath.resolve("downloaded.zip"), StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	public void getZip(String user, String repo) {
		// Feign 클라이언트를 통해 ZIP 파일 내용 받아오기
		ResponseEntity<Resource> responseEntity = githubFeignClient.getZipFiles(user, repo);

		// 환경변수로 지정한 경로 가져오기
		String targetPathStr = env.getProperty("CLIENT_PATH");

		//서버 내 파일 저장
		try {
			Path targetPath = Paths.get(targetPathStr);
			saveZipFileFromResponse(responseEntity, targetPath);

		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

}