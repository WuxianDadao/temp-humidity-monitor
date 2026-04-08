package com.iot.temphumidity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.iot.temphumidity.repository.postgresql")
@EnableJpaAuditing
public class BasicTestApp {
    public static void main(String[] args) {
        SpringApplication.run(BasicTestApp.class, args);
    }
}