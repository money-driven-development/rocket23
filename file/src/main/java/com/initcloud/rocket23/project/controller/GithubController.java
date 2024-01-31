package com.initcloud.rocket23.project.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.project.dto.GithubDto;
import com.initcloud.rocket23.project.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rocket")
@RequiredArgsConstructor
public class GithubController {

	private final GithubService githubService;

	@GetMapping("/repos/{user}")
	public ResponseDto<List<GithubDto.RepositoryInfo>> repositoryList(
            @PathVariable("user") String user
    ) {
		List<GithubDto.RepositoryInfo> dtos = githubService.getRepositories(user);
		List<GithubDto.RepositoryInfo> response = dtos.stream()
			.map(GithubDto.RepositoryInfo::toApiRepository)
			.collect(Collectors.toList());
		return new ResponseDto<>(response);
	}

	@GetMapping("/repos/{user}/{repo}")
	public ResponseDto<List<GithubDto.Contents>> repositoryDetails(
            @PathVariable("user") String user,
		    @PathVariable("repo") String repo,
            @Nullable @RequestParam("ref") String branch
    ) {
		List<GithubDto.Contents> dtos = githubService.getRepository(user, repo, branch);

		return new ResponseDto<>(dtos);
	}

	@GetMapping("/repos/{user}/{repo}/git/blobs/{hash}/{teamCode}/{projectCode}")
	public ResponseDto<GithubDto.File> gitFiles(
            @PathVariable("user") String user,
            @PathVariable("repo") String repo,
		    @PathVariable("hash") String hash,
			@PathVariable String teamCode,
			@PathVariable String projectCode,
            @Nullable @RequestParam("ref") String branch
    ) {
		GithubDto.File dtos = githubService.getBlobsFromGit(user, repo, hash, branch, teamCode, projectCode);

		return new ResponseDto<>(dtos);
	}

	@GetMapping("/repos/{user}/{repo}/zipball/{teamCode}/{projectCode}")
	public ResponseDto getZip(
			@PathVariable("user") String user,
			@PathVariable("repo") String repo,
			@PathVariable String teamCode,
			@PathVariable String projectCode
	) {
		githubService.getZip(user, repo, teamCode, projectCode);
		return new ResponseDto<>(null);
	}

}