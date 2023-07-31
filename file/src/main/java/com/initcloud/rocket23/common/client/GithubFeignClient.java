package com.initcloud.rocket23.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.initcloud.rocket23.github.dto.GithubDto;

@FeignClient(name = "feignClient", contextId = "githubFeignClient", url = "https://api.github.com")
public interface GithubFeignClient {

	@GetMapping(value = "/users/{USER}/repos")
	List<GithubDto.RepositoryInfo> getRepositoryList(@RequestHeader("Authorization") String token,
		@PathVariable("USER") String user);

	/* Contents in Repository */
	@GetMapping(value = "/repos/{USER}/{REPO}/contents")
	List<GithubDto.File> getRepositoryDetails(@RequestHeader("Authorization") String token, @PathVariable("USER") String user,
		@PathVariable("REPO") String repo, @RequestParam("ref") String branch);

	@GetMapping(value = "/repos/{USER}/{REPO}/commits")
	List<Object> getCommitList(@RequestHeader("Authorization") String token, @PathVariable("USER") String user,
		@PathVariable("REPO") String repo, @RequestParam("ref") String branch);

	@GetMapping(value = "/repos/{USER}/{REPO}/commits/{HASH}")
	Object getCommitDetails(@RequestHeader("Authorization") String token, @PathVariable("USER") String user,
		@PathVariable("REPO") String repo, @PathVariable("HASH") String hash, @RequestParam("ref") String branch);

	/* Blobs from Repository */
	@GetMapping(value = "/repos/{USER}/{REPO}/git/blobs/{HASH}")
	void getFiles(@RequestHeader("Authorization") String token, @PathVariable("USER") String user,
		@PathVariable("REPO") String repo, @PathVariable("HASH") String hash, @RequestParam("ref") String branch);
}