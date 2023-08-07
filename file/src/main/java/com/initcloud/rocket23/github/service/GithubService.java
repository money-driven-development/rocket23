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
import com.initcloud.rocket23.security.dto.GithubToken;
import com.initcloud.rocket23.security.provider.JwtTokenProvider;
import com.initcloud.rocket23.user.Repository.OAuthTokenRepository;
import com.initcloud.rocket23.user.Repository.UserRepository;
import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.entity.UserOAuthToken;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubService {

	private final GithubFeignClient githubFeignClient;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final OAuthTokenRepository oAuthTokenRepository;
	private final RedisMessagePublisher redisMessagePublisher;
	private final GithubRepository githubRepository;

	@Transactional
	public GithubToken addToken(GithubToken tokens) {
		String username = jwtTokenProvider.getUsername();

		User requestUser = userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		UserOAuthToken saved;
		if (requestUser.getOAuthToken() != null)
			saved = oAuthTokenRepository.save(new UserOAuthToken(requestUser.getOAuthToken(), tokens, requestUser));
		else
			saved = oAuthTokenRepository.save(GithubToken.toEntity(tokens, requestUser));

		if (saved.getAccessToken().equals(tokens.getAccessToken()))
			return tokens;
		else
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
	}

	public List<GithubDto.RepositoryInfo> getRepositories(@NonNull String user) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getRepositoryList(token, user);
	}

	public List<GithubDto.Contents> getRepository(String user, String repo, String branch) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getRepositoryDetails(token, user, repo, branch);
	}

	public List<Object> getCommits(String user, String repo, String branch) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getCommitList(token, user, repo, branch);
	}

	public Object getCommit(String user, String repo, String hash, String branch) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getCommitDetails(token, user, repo, hash, branch);
	}

	public GithubDto.File getBlobsFromGit(String user, String repo, String hash, String branch) {
		String token = jwtTokenProvider.getToken();
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