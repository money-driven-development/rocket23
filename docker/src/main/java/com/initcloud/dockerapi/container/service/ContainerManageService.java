package com.initcloud.dockerapi.container.service;

import com.initcloud.dockerapi.container.dto.IaCScanRequestDto;

public interface ContainerManageService<T> {

	T executeContainer(Integer count, IaCScanRequestDto dir);

	T createContainerForStandBy(Integer count);

	T terminateContainer(String containerId);

	T pauseContainer(String containerId);
}
