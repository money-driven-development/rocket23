package com.initcloud.dockerapi.container.middleware;

import java.io.Closeable;
import java.io.IOException;
import java.io.PipedOutputStream;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Frame;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DockerCmdResultCallback extends ResultCallback.Adapter {
	private PipedOutputStream outputStream;

	public DockerCmdResultCallback(PipedOutputStream outputStream) throws IOException {
		this.outputStream = outputStream;
	}

	public ResultCallback<Frame> Adapter;

	@Override
	public void close() throws IOException {

	}

	@Override
	public void onStart(Closeable closeable) {

	}

	@Override
	public void onNext(Object object) {
		try {
			outputStream.write(((Frame)object).getPayload());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onError(Throwable throwable) {

	}

	@Override
	public void onComplete() {

	}
}
