package com.initcloud.dockerapi.container.service;

public interface ContainerService {

	void executeContainer(Integer count);

	void executeContainerForStandBy(Integer count);

	void terminateContainer(String containerId);

	void getContainer(String containerId);

	void getContainerList();

	void pauseContainer(String containerId);
}
