package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.entity.BaseEntity;
import com.iot.temphumidity.enums.SensorStatus;
import com.iot.temphumidity.enums.SensorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 传感器标签实体类 - 温湿度传感器标签信息
 */
@Entity
@Table(name = "sensor_tag", schema = "iot")
@Getter
@Setter
public class SensorTagEntity extends BaseEntity {
    
    /**
     * 传感器标签ID - 设备唯一标识符
     */
    @Column(name = "tag_id", nullable = false, unique = true, length = 50)
    private String tagId;
    
    /**
     * 传感器名称
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * 传感器类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "sensor_type", nullable = false, length = 30)
    private SensorType sensorType = SensorType.TEMPERATURE_HUMIDITY;
    
    /**
     * 传感器状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SensorStatus status = SensorStatus.INACTIVE;
    
    /**
     * 所属网关ID
     */
    @Column(name = "gateway_id", nullable = false)
    private Long gatewayId;
    
    /**
     * 所属设备ID
     */
    @Column(name = "device_id")
    private Long deviceId;
    
    /**
     * 安装位置
     */
    @Column(name = "location", length = 200)
    private String location;
    
    /**
     * 位置描述
     */
    @Column(name = "location_description", length = 500)
    private String locationDescription;
    
    /**
     * 经度
     */
    @Column(name = "longitude")
    private Double longitude;
    
    /**
     * 纬度
     */
    @Column(name = "latitude")
    private Double latitude;
    
    /**
     * 安装高度（米）
     */
    @Column(name = "installation_height")
    private Double installationHeight;
    
    /**
     * 安装时间
     */
    @Column(name = "installation_time")
    private LocalDateTime installationTime;
    
    /**
     * 最后激活时间
     */
    @Column(name = "last_activation_time")
    private LocalDateTime lastActivationTime;
    
    /**
     * 获取网关ID（字符串形式）
     */
    public String getGatewayId() {
        return gatewayId != null ? gatewayId.toString() : null;
    }
    
    /**
     * 获取设备ID（字符串形式）
     */
    public String getDeviceId() {
        return deviceId != null ? deviceId.toString() : null;
    }
    
    /**
     * 获取网关ID（Long形式）
     */
    public Long getGatewayIdLong() {
        return gatewayId;
    }
    
    /**
     * 获取设备ID（Long形式）
     */
    public Long getDeviceIdLong() {
        return deviceId;
    }
    
    /**
     * 最后数据上报时间
     */
    @Column(name = "last_data_report_time")
    private LocalDateTime lastDataReportTime;
    
    /**
     * 上报间隔（秒）
     */
    @Column(name = "report_interval_seconds")
    private Integer reportIntervalSeconds = 60;
    
    /**
     * 温度校准偏移值
     */
    @Column(name = "temperature_calibration_offset")
    private Double temperatureCalibrationOffset = 0.0;
    
    /**
     * 湿度校准偏移值
     */
    @Column(name = "humidity_calibration_offset")
    private Double humidityCalibrationOffset = 0.0;
    
    /**
     * 温度阈值上限
     */
    @Column(name = "temperature_threshold_high")
    private Double temperatureThresholdHigh = 40.0;
    
    /**
     * 温度阈值下限
     */
    @Column(name = "temperature_threshold_low")
    private Double temperatureThresholdLow = 0.0;
    
    /**
     * 湿度阈值上限
     */
    @Column(name = "humidity_threshold_high")
    private Double humidityThresholdHigh = 80.0;
    
    /**
     * 湿度阈值下限
     */
    @Column(name = "humidity_threshold_low")
    private Double humidityThresholdLow = 20.0;
    
    /**
     * 电池电量（最后上报）
     */
    @Column(name = "battery_level")
    private Double batteryLevel;
    
    /**
     * 信号强度（最后上报）
     */
    @Column(name = "rssi")
    private Integer rssi;
    
    /**
     * 固件版本
     */
    @Column(name = "firmware_version", length = 50)
    private String firmwareVersion;
    
    /**
     * 硬件版本
     */
    @Column(name = "hardware_version", length = 50)
    private String hardwareVersion;
    
    /**
     * 生产批次号
     */
    @Column(name = "batch_number", length = 50)
    private String batchNumber;
    
    /**
     * 生产日期
     */
    @Column(name = "manufacture_date")
    private LocalDateTime manufactureDate;
    
    /**
     * 质保期（月）
     */
    @Column(name = "warranty_months")
    private Integer warrantyMonths = 24;
    
    /**
     * 备注信息
     */
    @Column(name = "remarks", length = 1000)
    private String remarks;
    
    /**
     * 所属网关（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", referencedColumnName = "id", insertable = false, updatable = false)
    private GatewayEntity gateway;
    
    /**
     * 所属设备（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DeviceEntity device;
    
    /**
     * 默认构造函数
     */
    public SensorTagEntity() {
        super();
    }
    
    /**
     * 带基础信息的构造函数
     */
    public SensorTagEntity(String tagId, String name, Long gatewayId) {
        this.tagId = tagId;
        this.name = name;
        this.gatewayId = gatewayId;
        this.status = SensorStatus.INACTIVE;
    }
    
    /**
     * 激活传感器
     */
    public void activate() {
        this.status = SensorStatus.ACTIVE;
        this.lastActivationTime = LocalDateTime.now();
    }
    
    /**
     * 停用传感器
     */
    public void deactivate() {
        this.status = SensorStatus.INACTIVE;
    }
    
    /**
     * 标记传感器异常
     */
    public void markAsAbnormal() {
        this.status = SensorStatus.ABNORMAL;
    }
    
    /**
     * 更新数据上报时间
     */
    public void updateDataReportTime() {
        this.lastDataReportTime = LocalDateTime.now();
        if (this.status == SensorStatus.INACTIVE) {
            this.status = SensorStatus.ACTIVE;
            if (this.lastActivationTime == null) {
                this.lastActivationTime = LocalDateTime.now();
            }
        }
    }
    
    /**
     * 更新电池电量和信号强度
     */
    public void updateBatteryAndRSSI(Double batteryLevel, Integer rssi) {
        this.batteryLevel = batteryLevel;
        this.rssi = rssi;
    }
    
    /**
     * 检查传感器是否在线
     */
    public boolean isOnline() {
        return SensorStatus.ACTIVE.equals(this.status);
    }
    
    /**
     * 检查传感器是否异常
     */
    public boolean isAbnormal() {
        return SensorStatus.ABNORMAL.equals(this.status);
    }
    
    /**
     * 检查电池电量是否低
     */
    public boolean isBatteryLow() {
        return batteryLevel != null && batteryLevel < 2.0; // 2V以下为低电量
    }
    
    /**
     * 检查信号强度是否弱
     */
    public boolean isSignalWeak() {
        return rssi != null && rssi < -80; // RSSI小于-80dBm为弱信号
    }
    
    /**
     * 检查是否超时（最后上报时间超过上报间隔的3倍）
     */
    public boolean isTimeout() {
        if (lastDataReportTime == null) return true;
        return LocalDateTime.now().minusSeconds(reportIntervalSeconds * 3L)
                .isAfter(lastDataReportTime);
    }
    
    /**
     * 获取传感器基本信息
     */
    public String getSensorInfo() {
        return String.format("传感器[%s] %s - %s", tagId, name, status.getDisplayName());
    }
    
    /**
     * 获取传感器位置信息
     */
    public String getLocationInfo() {
        if (location != null) {
            return location;
        }
        if (gateway != null && gateway.getLocation() != null) {
            return gateway.getLocation() + " (传感器)";
        }
        return "未指定位置";
    }
}