package com.initcloud.dockerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableWebMvc
@EnableOpenApi
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableAspectJAutoProxy
public class DockerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerApiApplication.class, args);
    }

}
