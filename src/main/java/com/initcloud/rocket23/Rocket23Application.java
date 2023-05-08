package com.initcloud.rocket23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Rocket23Application {

    public static void main(String[] args) {
        SpringApplication.run(Rocket23Application.class, args);
    }

}
