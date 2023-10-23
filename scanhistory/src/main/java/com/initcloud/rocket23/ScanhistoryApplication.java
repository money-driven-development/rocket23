package com.initcloud.rocket23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableFeignClients(basePackages = {"com.initcloud.rocket23.common.feign"})
@EnableJpaAuditing
@SpringBootApplication
public class ScanhistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScanhistoryApplication.class, args);
    }

}
