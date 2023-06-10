package com.initcloud.dockerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cache.annotation.EnableCaching;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class DockerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerApiApplication.class, args);
    }

}
