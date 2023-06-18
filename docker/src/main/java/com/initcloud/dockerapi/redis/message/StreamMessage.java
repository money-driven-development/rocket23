package com.initcloud.dockerapi.redis.message;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamMessage<T> {

	private enum MessageType {
		SCAN_START, // 스캔 시작
		SCAN_COMPLETE, // 스캔 완료
		SCAN_FAULT // 스캔 실패
	}

	/* 파일 업로드 경로 */
	private String directory;
	private MessageType messageType;

	private Boolean hasBody;

	private T body;

	/**
	 * 성공 시
	 */
	public StreamMessage(String directory, T body) {
		this.directory = directory;
		this.messageType = MessageType.SCAN_COMPLETE;
		this.hasBody = true;
		this.body = body;
	}

	/**
	 * 실패 시
	 */
	@Builder(builderClassName = "streamBuilder", builderMethodName = "redisStreamFailedBuilder")
	public StreamMessage(String directory) {
		this.directory = directory;
		this.messageType = MessageType.SCAN_FAULT;
		this.hasBody = false;
		this.body = null;
	}

	/**
	 * 실패 시 builder 를 통해 메시지 생성
	 */
	public static StreamMessage<Object> toFailedStreamMessage(String directory) {
		return StreamMessage.redisStreamFailedBuilder()
			.directory(directory)
			.build();
	}
}
