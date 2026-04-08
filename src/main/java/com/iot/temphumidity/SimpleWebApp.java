package com.iot.temphumidity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
@RestController
public class SimpleWebApp {
    
    @GetMapping("/")
    public String home() {
        return "Spring Boot IoT Temperature Humidity Monitor - Simple Web Version";
    }
    
    @GetMapping("/health")
    public String health() {
        return "{\"status\":\"UP\", \"timestamp\":\"" + System.currentTimeMillis() + "\"}";
    }
    
    @GetMapping("/api/status")
    public String status() {
        return "{\"application\":\"IoT Monitor\", \"version\":\"1.0.0-simple\", \"status\":\"running\"}";
    }
    
    public static void main(String[] args) {
        SpringApplication.run(SimpleWebApp.class, args);
    }
}