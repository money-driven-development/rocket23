package com.initcloud.rocket23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FileApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileApiApplication.class, args);
	}
}