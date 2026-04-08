package com.iot.temphumidity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TempHumidityMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TempHumidityMonitorApplication.class, args);
    }
}