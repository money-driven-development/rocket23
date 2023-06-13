package com.initcloud.dockerapi.container.service;

import java.util.List;

public interface ContainerInspectService<T> {

	T getContainerDetails(String containerId);

	T getContainerMetrics(String containerId);

	List getContainerList();

}
