package com.iot.temphumidity.dto.device;

import com.iot.temphumidity.entity.Device;
import com.iot.temphumidity.entity.Gateway;
import com.iot.temphumidity.enums.DeviceStatus;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 设备数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    
    /**
     * 设备ID
     */
    private String deviceId;
    
    /**
     * 设备名称
     */
    private String deviceName;
    
    /**
     * ICCID
     */
    private String iccid;
    
    /**
     * IMEI
     */
    private String imei;
    
    /**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 设备状态
     */
    private DeviceStatus status;
    
    /**
     * 设备位置
     */
    private String location;
    
    /**
     * 纬度
     */
    private Double latitude;
    
    /**
     * 经度
     */
    private Double longitude;
    
    /**
     * 设备厂商
     */
    private String manufacturer;
    
    /**
     * 设备型号
     */
    private String model;
    
    /**
     * 设备序列号
     */
    private String serialNumber;
    
    /**
     * 固件版本
     */
    private String firmwareVersion;
    
    /**
     * 硬件版本
     */
    private String hardwareVersion;
    
    /**
     * 网络类型
     */
    private String networkType;
    
    /**
     * 信号强度
     */
    private Integer signalStrength;
    
    /**
     * 电池电量（0-100）
     */
    private Integer batteryLevel;
    
    /**
     * 电池电压
     */
    private Double batteryVoltage;
    
    /**
     * 温度
     */
    private Double temperature;
    
    /**
     * 湿度
     */
    private Double humidity;
    
    /**
     * 最后数据接收时间
     */
    private LocalDateTime lastDataReceived;
    
    /**
     * 最后配置更新时间
     */
    private LocalDateTime lastConfigUpdate;
    
    /**
     * 数据上报间隔（秒）
     */
    private Integer dataIntervalSeconds;
    
    /**
     * 心跳间隔（秒）
     */
    private Integer heartbeatIntervalSeconds;
    
    /**
     * 最大重试次数
     */
    private Integer maxRetryCount;
    
    /**
     * 重试间隔（秒）
     */
    private Integer retryIntervalSeconds;
    
    /**
     * 是否启用报警
     */
    private Boolean alarmEnabled;
    
    /**
     * 报警配置（JSON）
     */
    private String alarmConfig;
    
    /**
     * 元数据（JSON）
     */
    private String metadata;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 网关ID
     */
    private String gatewayId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
    
    /**
     * 从实体转换
     */
    public static DeviceDTO fromEntity(Device entity) {
        if (entity == null) {
            return null;
        }
        
        return DeviceDTO.builder()
                .deviceId(entity.getDeviceId())
                .deviceName(entity.getDeviceName())
                .iccid(entity.getIccid())
                .imei(entity.getImei())
                .deviceType(entity.getDeviceType())
                .status(entity.getStatus())
                .location(entity.getLocation())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .manufacturer(entity.getManufacturer())
                .model(entity.getModel())
                .serialNumber(entity.getSerialNumber())
                .firmwareVersion(entity.getFirmwareVersion())
                .hardwareVersion(entity.getHardwareVersion())
                .networkType(entity.getNetworkType())
                .signalStrength(entity.getSignalStrength())
                .batteryLevel(entity.getBatteryLevel())
                .batteryVoltage(entity.getBatteryVoltage())
                .temperature(entity.getTemperature())
                .humidity(entity.getHumidity())
                .lastDataReceived(entity.getLastDataReceived())
                .lastConfigUpdate(entity.getLastConfigUpdate())
                .dataIntervalSeconds(entity.getDataIntervalSeconds())
                .heartbeatIntervalSeconds(entity.getHeartbeatIntervalSeconds())
                .maxRetryCount(entity.getMaxRetryCount())
                .retryIntervalSeconds(entity.getRetryIntervalSeconds())
                .alarmEnabled(entity.getAlarmEnabled())
                .alarmConfig(entity.getAlarmConfig())
                .metadata(entity.getMetadata())
                .notes(entity.getNotes())
                .gatewayId(entity.getGateway() != null ? String.valueOf(entity.getGateway().getId()) : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }
    
    /**
     * 转换为实体
     */
    public Device toEntity() {
        Device.DeviceBuilder builder = Device.builder()
                .deviceId(this.deviceId)
                .deviceName(this.deviceName)
                .iccid(this.iccid)
                .imei(this.imei)
                .deviceType(this.deviceType)
                .status(this.status != null ? this.status : DeviceStatus.ONLINE)
                .location(this.location)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .manufacturer(this.manufacturer)
                .model(this.model)
                .serialNumber(this.serialNumber)
                .firmwareVersion(this.firmwareVersion)
                .hardwareVersion(this.hardwareVersion)
                .networkType(this.networkType)
                .signalStrength(this.signalStrength)
                .batteryLevel(this.batteryLevel)
                .batteryVoltage(this.batteryVoltage)
                .temperature(this.temperature)
                .humidity(this.humidity)
                .lastDataReceived(this.lastDataReceived)
                .lastConfigUpdate(this.lastConfigUpdate)
                .dataIntervalSeconds(this.dataIntervalSeconds != null ? this.dataIntervalSeconds : 300)
                .heartbeatIntervalSeconds(this.heartbeatIntervalSeconds != null ? this.heartbeatIntervalSeconds : 600)
                .maxRetryCount(this.maxRetryCount != null ? this.maxRetryCount : 3)
                .retryIntervalSeconds(this.retryIntervalSeconds != null ? this.retryIntervalSeconds : 30)
                .alarmEnabled(this.alarmEnabled != null ? this.alarmEnabled : true)
                .alarmConfig(this.alarmConfig)
                .metadata(this.metadata)
                .notes(this.notes);
        
        // 注意：gatewayId不会在这里设置，需要在服务层处理
        return builder.build();
    }
    
    /**
     * 获取设备状态描述
     */
    public String getStatusDescription() {
        if (status == null) {
            return "未知";
        }
        
        switch (status) {
            case ONLINE:
                return "在线";
            case OFFLINE:
                return "离线";
            case FAULT:
                return "故障";
            case MAINTENANCE:
                return "维护中";
            case SUSPENDED:
                return "暂停";
            case DEPLOYED:
                return "已部署";
            case UNUSED:
                return "未使用";
            default:
                return status.toString();
        }
    }
    
    /**
     * 检查设备是否在线
     */
    public boolean isOnline() {
        return status == DeviceStatus.ONLINE;
    }
    
    /**
     * 检查设备是否需要维护
     */
    public boolean needsMaintenance() {
        return status == DeviceStatus.MAINTENANCE || status == DeviceStatus.FAULT;
    }
    
    /**
     * 获取电池状态
     */
    public String getBatteryStatus() {
        if (batteryLevel == null) {
            return "未知";
        }
        
        if (batteryLevel >= 80) {
            return "充足";
        } else if (batteryLevel >= 50) {
            return "良好";
        } else if (batteryLevel >= 20) {
            return "低电量";
        } else if (batteryLevel >= 10) {
            return "严重低电量";
        } else {
            return "即将耗尽";
        }
    }
    
    /**
     * 获取信号质量
     */
    public String getSignalQuality() {
        if (signalStrength == null) {
            return "未知";
        }
        
        if (signalStrength >= -50) {
            return "优秀";
        } else if (signalStrength >= -70) {
            return "良好";
        } else if (signalStrength >= -85) {
            return "一般";
        } else {
            return "差";
        }
    }
    
    /**
     * 获取设备位置信息
     */
    public String getLocationInfo() {
        if (latitude != null && longitude != null) {
            return String.format("%s (%.6f, %.6f)", location != null ? location : "", latitude, longitude);
        }
        return location != null ? location : "未设置位置";
    }
}