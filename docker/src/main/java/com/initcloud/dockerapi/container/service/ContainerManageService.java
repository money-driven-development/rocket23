package com.initcloud.dockerapi.container.service;

public interface ContainerManageService<T> {

	T executeContainer(Integer count);

	T createContainerForStandBy(Integer count);

	T terminateContainer(String containerId);

	T pauseContainer(String containerId);
}
