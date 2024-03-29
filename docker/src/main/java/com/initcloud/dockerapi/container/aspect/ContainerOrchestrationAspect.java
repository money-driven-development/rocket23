package com.initcloud.dockerapi.container.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.initcloud.dockerapi.container.annotation.ContainerLifeCycle;
import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.container.middleware.ContainerStrategyApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ContainerLifeCycle
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ContainerOrchestrationAspect {

	private final ContainerStrategyApi containerStrategyApi;

	/**
	 * 컨테이너 동작 전 스탠바이 컨테이너 수 조정
	 */
	@Before(value = "execution(* com.initcloud.dockerapi.container.service.DockerManageService.executeContainer(..)))")
	public void beforeContainerExecute() {
		containerStrategyApi.manageStandByContainerByStrategy();
	}
}
