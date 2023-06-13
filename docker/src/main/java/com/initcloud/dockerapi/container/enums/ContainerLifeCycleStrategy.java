package com.initcloud.dockerapi.container.enums;

public enum ContainerLifeCycleStrategy {

	/**
	 * [컨테이너 생성 전략]
	 * 컨테이너 동작 후 제거, 스탠바이 컨테이너는 별도로 생성하여 큐에 추가
	 * 스탠바이 컨테이너를 최대 수치로 유지하려 함.
	 * 전체 누적 컨테이너 수가 활성화(동작, 스탠바이) 컨테이너 수를 초과함.
	 * 동작 완료 후, 종료된 컨테이너를 제거해야 함.
	 */
	CREATE,

	/**
	 * [컨테이너 재사용 전략]
	 * 컨테이너 동작 완료를 대기, 완료된 컨테이너를 스탠바이 컨테이너로 큐에 추가
	 * 스탠바이 컨테이너를 최대 수치로 유지하지 않음.
	 * 전체 누적 컨테이너 수가 활성화(동작, 스탠바이) 컨테이너 수와 동일함.
	 * 동작 완료 후, 완료된 컨테이너를 큐에 다시 추가해야 함.
	 */
	REUSABLE,

	/**
	 * [온디맨드 컨테이너 실행 전략]
	 * 요청 시 컨테이너를 생성, 완료된 컨테이너는 삭제함.
	 * 스탠바이 컨테이너를 사용하지 않음.
	 * 큐는 생성되어 있음.
	 */
	ON_DEMAND_CREATE
}
