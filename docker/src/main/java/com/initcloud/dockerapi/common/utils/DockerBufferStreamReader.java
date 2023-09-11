package com.initcloud.dockerapi.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DockerBufferStreamReader {

	public static StringBuilder outputStreamToStringBuilder(PipedInputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder output = new StringBuilder();
		String rawResult;

		while ((rawResult = br.readLine()) != null) {
			if (rawResult.contains("framework ]"))
				continue;

			output.append(rawResult);
		}

		return output;
	}
}
