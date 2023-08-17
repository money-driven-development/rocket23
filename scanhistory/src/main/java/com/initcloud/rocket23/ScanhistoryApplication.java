package com.initcloud.rocket23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@EnableFeignClients(basePackages = {"com.initcloud.rocket23.common.feign"})
@EnableJpaAuditing
@SpringBootApplication
public class ScanhistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScanhistoryApplication.class, args);
    }

}
