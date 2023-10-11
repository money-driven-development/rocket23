package com.initcloud.dockerapi.container.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class DockerClientProperties {

	@Value("${docker.host.unix}")
	private String dockerUnixHost;

	@Value("${docker.host.ssl}")
	private String dockerSSL;

	@Value("${docker.volume.host}")
	private String volumeHostRoot = "~/nas/uploads/";

	@Value("${docker.volume.container}")
	private String volumeContainerRoot = "/app/uploads/";
}
