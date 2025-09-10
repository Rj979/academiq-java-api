package com.academiq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.academiq.repository")
@EntityScan(basePackages = "com.academiq.model")
public class AcademiqApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademiqApplication.class, args);
    }
}