package com.initcloud.rocket23.common.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.initcloud.rocket23.github.dto.GithubDto;

import feign.Headers;

@FeignClient(name = "feignClient", contextId = "githubFeignClient", url = "https://api.github.com")
public interface GithubFeignClient {

	@GetMapping(value = "/users/{USER}/repos")
	List<GithubDto.RepositoryInfo> getRepositoryList(@PathVariable("USER") String user);

	/* Contents in Repository */
	@GetMapping(value = "/repos/{USER}/{REPO}/contents")
	List<GithubDto.Contents> getRepositoryDetails(@PathVariable("USER") String user, @PathVariable("REPO") String repo,
		@RequestParam("ref") String branch);

	@GetMapping(value = "/repos/{USER}/{REPO}/commits")
	List<Object> getCommitList(@PathVariable("USER") String user, @PathVariable("REPO") String repo,
		@RequestParam("ref") String branch);

	@GetMapping(value = "/repos/{USER}/{REPO}/commits/{HASH}")
	Object getCommitDetails(@PathVariable("USER") String user, @PathVariable("REPO") String repo,
		@PathVariable("HASH") String hash, @RequestParam("ref") String branch);

	/* Blobs from Repository */
	@GetMapping(value = "/repos/{USER}/{REPO}/git/blobs/{HASH}")
	GithubDto.File getFiles(@PathVariable("USER") String user, @PathVariable("REPO") String repo,
		@PathVariable("HASH") String hash, @RequestParam("ref") String branch);

	/* Downloads Repository */
	@GetMapping(value = "/repos/{USER}/{REPO}/zipball")
	@Headers("Accept: application/vnd.github+json")
	ResponseEntity<Resource> getZipFiles(@PathVariable("USER") String user, @PathVariable("REPO") String repo);
}