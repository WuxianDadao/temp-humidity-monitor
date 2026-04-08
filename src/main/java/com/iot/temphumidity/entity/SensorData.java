package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 传感器数据实体（PostgreSQL存储）
 * 注意：时序数据存储在TDengine中，此实体用于业务关联数据
 */
@Data
@Entity
@Table(name = "sensor_data")
public class SensorData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "sensor_id", nullable = false, length = 100)
    private String sensorId;
    
    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;
    
    @Column(name = "gateway_id", length = 100)
    private String gatewayId;
    
    @Column(name = "tdengine_super_table_name", length = 100)
    private String tdengineSuperTableName;
    
    @Column(name = "tdengine_timestamp")
    private LocalDateTime tdengineTimestamp;
    
    @Column(name = "temperature")
    private Double temperature;
    
    @Column(name = "humidity")
    private Double humidity;
    
    @Column(name = "battery_voltage")
    private Double batteryVoltage;
    
    @Column(name = "signal_strength")
    private Double signalStrength;
    
    @Column(name = "data_quality", length = 20)
    private String dataQuality;
    
    @Column(name = "sampled_at", nullable = false)
    private LocalDateTime sampledAt;
    
    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    @Column(name = "stored_in_tdengine", nullable = false)
    private Boolean storedInTdengine = false;
    
    @Column(name = "tdengine_insert_id")
    private String tdengineInsertId;
    
    @Column(name = "alert_checked", nullable = false)
    private Boolean alertChecked = false;
    
    @Column(name = "alert_triggered")
    private Boolean alertTriggered;
    
    @Column(name = "alert_id")
    private String alertId;
    
    @Column(name = "metadata_json", columnDefinition = "TEXT")
    private String metadataJson;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", insertable = false, updatable = false)
    private Device device;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", referencedColumnName = "gateway_id", insertable = false, updatable = false)
    private DeviceGateway gateway;
    
    /**
     * 是否为有效的传感器数据
     */
    public boolean isValid() {
        return temperature != null || humidity != null;
    }
    
    /**
     * 获取数据质量等级
     */
    public String calculateDataQuality() {
        if (temperature == null && humidity == null) {
            return "INVALID";
        }
        
        boolean tempValid = temperature != null && temperature >= -40 && temperature <= 100;
        boolean humidityValid = humidity != null && humidity >= 0 && humidity <= 100;
        
        if (tempValid && humidityValid) {
            return "GOOD";
        } else if (tempValid || humidityValid) {
            return "FAIR";
        } else {
            return "POOR";
        }
    }
    
    /**
     * 是否为时序数据（应存储在TDengine中）
     */
    public boolean isTimeSeriesData() {
        return temperature != null || humidity != null;
    }
}