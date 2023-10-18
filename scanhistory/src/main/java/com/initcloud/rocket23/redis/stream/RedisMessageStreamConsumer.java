package com.initcloud.rocket23.redis.stream;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.StreamMessageId;
import org.redisson.client.RedisException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Deprecated
@Slf4j
@Service
public class RedisMessageStreamConsumer {

    private final RStream<String, String> scanRequestStream;
    private String consumerGroupName = "consumerGroupContainer";
    private String consumerName = "consumerContainerDocker";

    public RedisMessageStreamConsumer(RStream<String, String> scanRequestStream) {
        this.scanRequestStream = scanRequestStream;

        try {
            scanRequestStream.createGroup(consumerGroupName, StreamMessageId.ALL);
        } catch (RedisException e) {
            log.warn("[EXISTED GROUP] - {}", consumerGroupName);
        }
    }

    /**
     * Pending 상태의 메시지 Listen
     */
    public void consumePendingMessage() {

    }

    /**
     * Pending 상태의 메시지 처리에 대한 응답
     */
    private void claimMessageProcessing() {

    }

    /**
     * 메시지 Listen
     * Todo -
     */
    public void consumeRedisStream() {

        Map<StreamMessageId, Map<String, String>> messages = scanRequestStream.readGroup(consumerGroupName,
                consumerName);

        for (Map.Entry<StreamMessageId, Map<String, String>> entry : messages.entrySet()) {
            Map<String, String> msg = entry.getValue();
            log.info("[RECV MESSAGE] - {}", msg);
            scanRequestStream.ack(consumerGroupName, entry.getKey());
        }
    }
}
