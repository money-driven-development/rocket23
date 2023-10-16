package com.initcloud.dockerapi.redis.pubsub;

import com.initcloud.dockerapi.redis.message.ScanStreamMessage;
import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.dockerapi.common.enums.ResponseCode;
import com.initcloud.dockerapi.common.exception.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

    @Qualifier("topicScan")
    private final RTopic topicScan;

    @Qualifier("topicContainer")
    private final RTopic topicContainer;

    private final ObjectMapper objectMapper;

    public void publishContainerMessage(String data) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(data);

            if (!data.isEmpty()) {
                topicContainer.publish(jsonMessage);
            }
        } catch (JsonProcessingException e) {
            // Todo - 이 예외는 응답으로 반환할 것이 아니므로 별도의 처리가 필요.
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }
    }

    public void publishScanMessage(String data, String teamCode, String projectCode) {
        try {
            if (!data.isEmpty()) {

                /** Todo - written 23.10.13
                 * ScanStreamMessage.serializeToScanResult(...)
                 * 전달할 json 의 필드를 조절해서최적화 할 수 있음.
                 * 또는, json 이 아닌 protobuf, avro, gin 등 바이너리로 변경 한다면 전송하는 네트워크 비용을 최적화 할 수 있음
                 */
                String result = ScanStreamMessage.serializeToScanResult(data, teamCode, projectCode);
                topicScan.publish(result);
            }
        } catch (Exception e) {
            // Todo - 이 예외는 응답으로 반환할 것이 아니므로 별도의 처리가 필요.
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }
    }

    // 팀, 프로젝트 코드를 전달하지 않음.
    public void publishScanMessage(String data) {
        try {
            if (!data.isEmpty()) {
                topicScan.publish(data);
            }
        } catch (Exception e) {
            // Todo - 이 예외는 응답으로 반환할 것이 아니므로 별도의 처리가 필요.
            throw new ApiException(ResponseCode.INVALID_REQUEST);
        }
    }
}
