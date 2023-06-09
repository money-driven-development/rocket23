package com.initcloud.dockerapi.container.client;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerClientProperties {

	@Value("${docker.host.unix}")
	private String dockerUnixHost;

	@Value ("${docker.host.ssl}")
	private String dockerSSL;
}
