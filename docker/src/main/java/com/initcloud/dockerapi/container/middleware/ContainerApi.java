package com.initcloud.dockerapi.container.middleware;

public interface ContainerApi {

	public void create();

	public void execute();

	public void get();

	public void terminate();

	void pull() throws InterruptedException;
}
