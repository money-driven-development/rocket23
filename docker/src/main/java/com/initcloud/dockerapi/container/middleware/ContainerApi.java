package com.initcloud.dockerapi.container.middleware;

public interface ContainerApi<T> {

	/* 생성 */
	T create();

	/* 실행 */
	T execute();

	/* 목록 조회 */
	T get();

	/* 세부 조회 */
	T inspect(String containerId);

	/* 일시정지 */
	T stop(String containerId);

	/* 종료 */
	T terminate(String containerId);
}
