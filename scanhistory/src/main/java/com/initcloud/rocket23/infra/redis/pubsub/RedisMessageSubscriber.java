package com.initcloud.rocket23.infra.redis.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.service.ScanService;
import io.netty.handler.codec.DecoderException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageSubscriber {

    @Qualifier("topicScan")
    private final RTopic topicScan;

    private final ObjectMapper objectMapper;
    private final ScanService scanService;

    @PostConstruct
    public void initialize() {
        subscribeScanChannel();
    }

    public void subscribeScanChannel() {
        log.info("[SUBSCRIBE] - Scan Channel");
        topicScan.addListener(String.class, (channel, data) -> {
            try {
                log.info("[RECEIVED] {} - {}", channel, data);
                scanService.saveCheckovScan(data);
            } catch (DecoderException e) {
                log.warn("[DECODE ERROR] - from {}, about {}", channel, e.getMessage());
            } catch (Exception e) {
                log.warn("[DATA BIND ERROR] - from {}, about {}", channel, e.getMessage());
            }
        });
    }
}
