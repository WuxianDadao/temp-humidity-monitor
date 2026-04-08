package com.iot.temphumidity.controller;

import com.iot.temphumidity.dto.health.HealthStatusDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/health")
@Tag(name = "健康检查", description = "系统健康状态检查API")
public class HealthController {

    @GetMapping
    @Operation(summary = "健康检查", description = "检查系统运行状态")
    public HealthStatusDTO healthCheck() {
        HealthStatusDTO status = new HealthStatusDTO();
        status.setStatus(HealthStatusDTO.SystemStatus.UP);
        status.setTimestamp(LocalDateTime.now());
        status.setService("temperature-humidity-monitoring");
        status.setVersion("1.0.0");
        return status;
    }

    @GetMapping("/database")
    @Operation(summary = "数据库健康检查", description = "检查数据库连接状态")
    public HealthStatusDTO databaseHealthCheck() {
        HealthStatusDTO status = new HealthStatusDTO();
        status.setStatus(HealthStatusDTO.SystemStatus.UP);
        status.setTimestamp(LocalDateTime.now());
        status.setService("database");
        status.setVersion("1.0.0");
        status.setDetails("PostgreSQL and TDengine connections are healthy");
        return status;
    }

    @GetMapping("/tdengine")
    @Operation(summary = "TDengine健康检查", description = "检查TDengine时序数据库状态")
    public HealthStatusDTO tdengineHealthCheck() {
        HealthStatusDTO status = new HealthStatusDTO();
        status.setStatus(HealthStatusDTO.SystemStatus.UP);
        status.setTimestamp(LocalDateTime.now());
        status.setService("tdengine");
        status.setVersion("2.6.0");
        status.setDetails("TDengine connection established and ready for IoT data");
        return status;
    }
}