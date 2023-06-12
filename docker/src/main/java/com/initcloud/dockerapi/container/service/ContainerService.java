package com.initcloud.dockerapi.container.service;

import java.util.List;

public interface ContainerService<T> {

	T executeContainer(Integer count);

	T executeContainerForStandBy(Integer count);

	T terminateContainer(String containerId);

	T getContainerDetails(String containerId);

	List getContainerList();

	T pauseContainer(String containerId);
}
