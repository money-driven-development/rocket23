package com.initcloud.dockerapi.container.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.initcloud.dockerapi.container.dto.IaCScanRequestDto;
import org.json.simple.parser.ParseException;

public interface ContainerManageService<T> {

	T executeContainer(Integer count, IaCScanRequestDto dir) throws ParseException, JsonProcessingException;

	T createContainerForStandBy(Integer count);

	T terminateContainer(String containerId);

	T pauseContainer(String containerId);
}
