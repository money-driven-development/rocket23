package com.initcloud.dockerapi.container.client;

public interface ContainerApi<T> {

	/* 생성 */
	public T create();

	/* 실행 */
	public T execute();

	/* 목록 조회 */
	public T get();

	/* 세부 조회 */
	public T inspect(String containerId);

	/* 일시정지 */
	public T stop(String containerId);

	/* 종료 */
	public T terminate(String containerId);
}
