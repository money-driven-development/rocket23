package com.initcloud.dockerapi.container.middleware;

public interface ContainerApi {

	/* 생성 */
	public void create();

	/* 실행 */
	public void execute();

	/* 목록 조회 */
	public void get();

	/* 세부 조회 */
	public void inspect(String containerId);

	/* 종료 */
	public void terminate(String containerId);
}
