package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 传感器配置实体
 * 存储传感器的配置信息，如报警阈值、采样频率等
 */
@Entity
@Table(name = "sensor_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorConfig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sensor_tag_id", nullable = false)
    private Long sensorTagId;
    
    @Column(name = "device_id")
    private Long deviceId;
    
    @Column(name = "gateway_id")
    private Long gatewayId;
    
    // 温度报警配置
    @Column(name = "temperature_alarm_enabled", nullable = false)
    private Boolean temperatureAlarmEnabled = true;
    
    @Column(name = "temperature_alarm_threshold_min", precision = 5, scale = 2)
    private BigDecimal temperatureAlarmThresholdMin = new BigDecimal("-20.00");
    
    @Column(name = "temperature_alarm_threshold_max", precision = 5, scale = 2)
    private BigDecimal temperatureAlarmThresholdMax = new BigDecimal("80.00");
    
    // 湿度报警配置
    @Column(name = "humidity_alarm_enabled", nullable = false)
    private Boolean humidityAlarmEnabled = true;
    
    @Column(name = "humidity_alarm_threshold_min", precision = 5, scale = 2)
    private BigDecimal humidityAlarmThresholdMin = new BigDecimal("0.00");
    
    @Column(name = "humidity_alarm_threshold_max", precision = 5, scale = 2)
    private BigDecimal humidityAlarmThresholdMax = new BigDecimal("100.00");
    
    // 采样配置
    @Column(name = "sampling_interval_seconds", nullable = false)
    private Integer samplingIntervalSeconds = 60;
    
    @Column(name = "data_retention_days", nullable = false)
    private Integer dataRetentionDays = 365;
    
    // 网络配置
    @Column(name = "mqtt_topic", length = 255)
    private String mqttTopic;
    
    @Column(name = "http_endpoint", length = 255)
    private String httpEndpoint;
    
    // 电池配置
    @Column(name = "battery_alarm_threshold", precision = 5, scale = 2)
    private BigDecimal batteryAlarmThreshold = new BigDecimal("20.00");
    
    // 信号配置
    @Column(name = "signal_rssi_alarm_threshold")
    private Integer signalRssiAlarmThreshold = -80;
    
    // 状态
    @Column(name = "config_status", length = 20)
    private String configStatus = "ACTIVE";
    
    @Column(name = "last_config_sync_time")
    private LocalDateTime lastConfigSyncTime;
    
    @Column(name = "last_data_receive_time")
    private LocalDateTime lastDataReceiveTime;
    
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
    
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime = LocalDateTime.now();
    
    // 关联关系
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_tag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SensorTag sensorTag;
    
    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
    
    /**
     * 创建一个默认配置
     */
    public static SensorConfig createDefaultConfig(Long sensorTagId) {
        SensorConfig config = new SensorConfig();
        config.setSensorTagId(sensorTagId);
        config.setTemperatureAlarmEnabled(true);
        config.setTemperatureAlarmThresholdMin(new BigDecimal("-20.00"));
        config.setTemperatureAlarmThresholdMax(new BigDecimal("80.00"));
        config.setHumidityAlarmEnabled(true);
        config.setHumidityAlarmThresholdMin(new BigDecimal("0.00"));
        config.setHumidityAlarmThresholdMax(new BigDecimal("100.00"));
        config.setSamplingIntervalSeconds(60);
        config.setDataRetentionDays(365);
        config.setBatteryAlarmThreshold(new BigDecimal("20.00"));
        config.setSignalRssiAlarmThreshold(-80);
        config.setConfigStatus("ACTIVE");
        return config;
    }
}