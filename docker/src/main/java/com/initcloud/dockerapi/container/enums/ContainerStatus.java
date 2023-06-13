package com.initcloud.dockerapi.container.enums;

/**
 * Todo - 현재 상태 값이 도커 컨테이너에서 제공하는 상태와 상이해 추후 조정 필요.
 */
public enum ContainerStatus {

	STANDBY, // 사용 대기
	WARMUP,  // 스탠바이 대기
	RUNNING, // 사용 중
	STOPPED, // 정지
	ORPHAN,  // 관리 이탈
	TERMINATED // 종료됨
}
