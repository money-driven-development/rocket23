package com.initcloud.dockerapi.container.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContainerInitializationListener {

	/**
	 * 앱 구동 완료 후 동작
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent(ContextRefreshedEvent event) {

	}
}


