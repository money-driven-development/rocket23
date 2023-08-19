package com.initcloud.rocket23.github.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.github.dto.GithubDto;
import com.initcloud.rocket23.github.service.GithubService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@ApiOperation("Github access")
@RestController
@RequestMapping("/rocket23")
@RequiredArgsConstructor
public class GithubController {

	private final GithubService githubService;

	@ApiOperation(value = "Get Repository List", notes = "Get Repository List from Github.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", value = "Github user or organization", required = true, dataTypeClass = String.class)})
	@GetMapping("/repos/{user}")
	public ResponseDto<List<GithubDto.RepositoryInfo>> repositoryList(@PathVariable("user") String user) {
		List<GithubDto.RepositoryInfo> dtos = githubService.getRepositories(user);
		List<GithubDto.RepositoryInfo> response = dtos.stream()
			.map(GithubDto.RepositoryInfo::toApiRepository)
			.collect(Collectors.toList());
		return new ResponseDto<>(response);
	}

	@ApiOperation(value = "Get Repository Details", notes = "Get Repository Details from Github.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", value = "Github user or organization", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "repo", value = "Github repository", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", dataTypeClass = String.class)})
	@GetMapping("/repos/{user}/{repo}")
	public ResponseDto<List<GithubDto.Contents>> repositoryDetails(@PathVariable("user") String user,
		@PathVariable("repo") String repo, @Nullable @RequestParam("ref") String branch) {
		List<GithubDto.Contents> dtos = githubService.getRepository(user, repo, branch);

		return new ResponseDto<>(dtos);
	}

	@ApiOperation(value = "Download Blobs", notes = "Download zip files from Github repository, branch.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "hash", paramType = "path", value = "Commit Hash", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", dataTypeClass = String.class)})
	@GetMapping("/repos/{user}/{repo}/git/blobs/{hash}")
	public ResponseDto<?> gitFiles(@PathVariable("user") String user, @PathVariable("repo") String repo,
		@PathVariable("hash") String hash, @Nullable @RequestParam("ref") String branch) {
		GithubDto.File dtos = githubService.getBlobsFromGit(user, repo, hash, branch);

		return new ResponseDto<>(dtos);
	}

	@Deprecated(forRemoval = false)
	@ApiOperation(value = "Download Zip Files", notes = "Download zip files from Github repository", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true, dataTypeClass = String.class)})
	@GetMapping("/repos/{user}/{repo}/zipball")
	public ResponseDto<?> getZip(@PathVariable("user") String user, @PathVariable("repo") String repo) {
		githubService.getZip(user, repo);
		return new ResponseDto<>(null);
	}

}