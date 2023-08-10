package com.initcloud.rocket23.github.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			.contents(file.getContent())
			.encoding(file.getEncoding())
			.url(file.getUrl())
			.sha(file.getSha())
			.size(file.getSize())
			.nodeId(file.getNodeId())
			.build();

		redisMessagePublisher.publishFileMessage(RedisFileDto.toDto(uuid));
		githubRepository.save(githubEntity);

		return file;
	}

}