package com.initcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients(basePackages = {"com.initcloud.rocket23.common.feign"})
@EnableJpaAuditing
@SpringBootApplication
public class ScanHistoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScanHistoryApplication.class, args);
	}
}
