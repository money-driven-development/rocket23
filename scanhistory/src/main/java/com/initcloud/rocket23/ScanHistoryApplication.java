package com.initcloud.rocket23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.initcloud.rocket23.common.feign"})
@SpringBootApplication
public class ScanHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScanHistoryApplication.class, args);
	}
}
