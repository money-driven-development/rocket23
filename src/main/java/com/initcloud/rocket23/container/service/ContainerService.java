package com.initcloud.rocket23.container.service;

public interface ContainerService {

	void executeContainer();

	void executeContainerForStandBy();

	void terminateContainer();

	void getContainer();

	void getContainerList();

	void pauseContainer();
}
