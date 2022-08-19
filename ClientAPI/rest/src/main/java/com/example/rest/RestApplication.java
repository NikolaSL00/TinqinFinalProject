package com.example.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.rest",
        "com.example.api",
        "com.example.domain",
        "com.example.core"})
@EnableFeignClients(basePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example")
@EntityScan(basePackages = "com.example")
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}

//@EnableJpaRepositories(basePackages = "com.example.repository")
//@EntityScan(basePackages = "com.example.entity")