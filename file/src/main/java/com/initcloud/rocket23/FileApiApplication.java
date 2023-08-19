package com.initcloud.rocket23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableWebMvc
@EnableOpenApi
@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
public class FileApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileApiApplication.class, args);
	}
}