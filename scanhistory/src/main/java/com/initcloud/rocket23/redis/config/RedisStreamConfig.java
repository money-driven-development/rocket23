package com.initcloud.rocket23.redis.config;

import com.initcloud.dockerapi.container.dto.ContainerDto;
import com.initcloud.dockerapi.redis.message.ScanStreamMessage;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Deprecated
@Configuration
public class RedisStreamConfig {

    /**
     * [Input]
     * 스캔 요청을 위한 스트림
     */
    @Bean
    public RStream<String, String> redisScanRequestStream(RedissonClient redissonClient) {
        return redissonClient.getStream("scan.request");
    }

    /**
     * [Output]
     * 스캔 결과 반환을 위한 스트림
     */
    @Bean
    public RStream<String, ScanStreamMessage> redisScanResultStream(RedissonClient redissonClient) {
        return redissonClient.getStream("scan.response");
    }

    /**
     * [Output]
     * 컨테이너 상태 반환을 위한 스트림
     */
    @Bean
    public RStream<String, ContainerDto> redisContainerStream(RedissonClient redissonClient) {
        return redissonClient.getStream("container.docker");
    }
}
