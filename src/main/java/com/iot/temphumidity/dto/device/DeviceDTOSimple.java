package com.iot.temphumidity.dto.device;

import com.iot.temphumidity.entity.Device;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备数据传输对象（简化版 - 用于编译测试）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTOSimple {
    
    /**
     * 设备ID
     */
    private String id;
    
    /**
     * 设备名称
     */
    private String name;
    
    /**
     * 设备序列号
     */
    private String serialNumber;
    
    /**
     * 设备类型（字符串）
     */
    private String deviceType;
    
    /**
     * 设备型号
     */
    private String model;
    
    /**
     * 制造商
     */
    private String manufacturer;
    
    /**
     * 固件版本
     */
    private String firmwareVersion;
    
    /**
     * 硬件版本
     */
    private String hardwareVersion;
    
    /**
     * 设备状态（字符串）
     */
    private String status;
    
    /**
     * 设备位置
     */
    private String location;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 网关ID
     */
    private String gatewayId;
    
    /**
     * 配置JSON
     */
    private String configJson;
    
    /**
     * 元数据JSON
     */
    private String metadataJson;
    
    /**
     * 最后在线时间
     */
    private LocalDateTime lastOnlineTime;
    
    /**
     * 最后数据上报时间
     */
    private LocalDateTime lastDataReportTime;
    
    /**
     * 电量水平
     */
    private Integer batteryLevel;
    
    /**
     * 信号强度
     */
    private Integer signalStrength;
    
    /**
     * 纬度
     */
    private Double latitude;
    
    /**
     * 经度
     */
    private Double longitude;
    
    /**
     * 时区
     */
    private String timezone;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 标签
     */
    private List<String> tags;
    
    /**
     * 从实体转换
     */
    public static DeviceDTOSimple fromEntity(Device entity) {
        if (entity == null) {
            return null;
        }
        
        // 获取网关ID（需要处理可能的空指针）
        Long gatewayId = null;
        if (entity.getGateway() != null) {
            gatewayId = entity.getGateway().getId();
        }
        
        // 检查设备是否启用（基于状态）
        Boolean enabled = entity.getStatus() != null && entity.getStatus() == com.iot.temphumidity.enums.DeviceStatus.ONLINE;
        
        return DeviceDTOSimple.builder()
                .name(entity.getDeviceName())
                .serialNumber(entity.getSerialNumber())
                .deviceType(entity.getDeviceType())
                .model(entity.getModel())
                .manufacturer(entity.getManufacturer())
                .firmwareVersion(entity.getFirmwareVersion())
                .hardwareVersion(entity.getHardwareVersion())
                .status(entity.getStatus() != null ? entity.getStatus().toString() : null)
                .location(entity.getLocation())
                .userId(null) // Device实体没有直接的userId字段
                .gatewayId(gatewayId != null ? gatewayId.toString() : null)
                .configJson(entity.getAlarmConfig()) // 使用alarmConfig字段
                .metadataJson(entity.getMetadata()) // 使用metadata字段
                .lastOnlineTime(entity.getLastDataReceived()) // 使用lastDataReceived字段
                .lastDataReportTime(entity.getLastDataReceived()) // 使用lastDataReceived字段
                .batteryLevel(entity.getBatteryLevel())
                .signalStrength(entity.getSignalStrength())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .timezone("UTC") // 默认时区，因为实体中没有时区字段
                .enabled(enabled) // 基于状态计算是否启用
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .remark(entity.getNotes()) // 使用notes字段
                .tags(new ArrayList<String>()) // 实体中没有tags字段，返回空列表
                .build();
    }
}