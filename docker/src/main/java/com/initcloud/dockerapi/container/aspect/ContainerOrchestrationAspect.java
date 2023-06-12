package com.initcloud.dockerapi.container.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ContainerOrchestrationAspect {

	/**
	 * 컨테이너 동작 후 스탠바이 컨테이너 수 조정
	 */
	@AfterReturning(value = "execution(* com.initcloud.dockerapi.container.service.DockerService.executeContainer(..)) && args(containerId))")
	public void afterReturningContainerExecute(String containerId) {
		log.info("[EXECUTE] {}", containerId);

		/* Todo - 스탠바이 컨테이너 수를 조정하는 로직 추가 */
	}
}
