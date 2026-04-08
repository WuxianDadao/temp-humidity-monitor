package com.iot.temphumidity.dto.device;

import com.iot.temphumidity.entity.Device;
import com.iot.temphumidity.enums.DeviceStatus;
import com.iot.temphumidity.enums.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 设备创建数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreateDTO {
    
    /**
     * ICCID (SIM卡唯一标识)
     */
    @NotBlank(message = "ICCID不能为空")
    @Size(min = 1, max = 20, message = "ICCID长度必须在1-20个字符之间")
    private String iccid;
    
    /**
     * 设备序列号
     */
    @NotBlank(message = "设备序列号不能为空")
    @Size(min = 1, max = 100, message = "设备序列号长度必须在1-100个字符之间")
    private String serialNumber;
    
    /**
     * 设备名称
     */
    @NotBlank(message = "设备名称不能为空")
    @Size(min = 1, max = 128, message = "设备名称长度必须在1-128个字符之间")
    private String name;
    
    /**
     * 设备类型
     */
    @NotNull(message = "设备类型不能为空")
    private DeviceType deviceType;
    
    /**
     * 设备型号
     */
    @Size(max = 64, message = "设备型号长度不能超过64个字符")
    private String model;
    
    /**
     * 设备厂商
     */
    @Size(max = 64, message = "设备厂商长度不能超过64个字符")
    private String manufacturer;
    
    /**
     * 固件版本
     */
    @Size(max = 32, message = "固件版本长度不能超过32个字符")
    private String firmwareVersion;
    
    /**
     * 硬件版本
     */
    @Size(max = 32, message = "硬件版本长度不能超过32个字符")
    private String hardwareVersion;
    
    /**
     * 设备位置
     */
    @Size(max = 255, message = "设备位置长度不能超过255个字符")
    private String location;
    
    /**
     * 所属用户ID
     */
    private Long userId;
    
    /**
     * 所属网关ID
     */
    private Long gatewayId;
    
    /**
     * 设备配置JSON
     */
    private String configJson;
    
    /**
     * 设备元数据JSON
     */
    private String metadataJson;
    
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
    @Size(max = 50, message = "时区长度不能超过50个字符")
    private String timezone;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
    
    /**
     * 标签
     */
    @Size(max = 255, message = "标签长度不能超过255个字符")
    private String tags;
    
    /**
     * 传感器标签ID列表
     */
    private List<Long> tagIds;
    
    /**
     * 转换为实体
     */
    public Device toEntity() {
        return Device.builder()
                .serialNumber(this.serialNumber)
                .deviceName(this.name)  // 注意：Device实体字段是deviceName，不是name
                .deviceType(this.deviceType != null ? this.deviceType.name() : null)
                .model(this.model)
                .manufacturer(this.manufacturer)
                .firmwareVersion(this.firmwareVersion)
                .hardwareVersion(this.hardwareVersion)
                .status(DeviceStatus.OFFLINE) // 新设备默认为离线状态
                .location(this.location)
                .userId(this.userId)
                // 注意：Device实体类使用gateway字段而不是gatewayId
                // 网关对象需要在调用此方法前设置
                // .gateway(this.gateway) // 暂时注释掉，需要在调用方提供Gateway对象
                .alarmConfig(this.configJson)
                .metadata(this.metadataJson)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .timezone(this.timezone)
                .alarmEnabled(this.enabled != null ? this.enabled : true)
                .remark(this.remark)
                .tags(this.tags)
                .build();
    }
    
    /**
     * 验证数据
     */
    public boolean validate() {
        if (serialNumber == null || serialNumber.trim().isEmpty()) {
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (deviceType == null) {
            return false;
        }
        return true;
    }
    
    /**
     * 获取验证错误信息
     */
    public String getValidationError() {
        if (serialNumber == null || serialNumber.trim().isEmpty()) {
            return "设备序列号不能为空";
        }
        if (name == null || name.trim().isEmpty()) {
            return "设备名称不能为空";
        }
        if (deviceType == null) {
            return "设备类型不能为空";
        }
        
        // 验证位置坐标
        if (latitude != null && (latitude < -90 || latitude > 90)) {
            return "纬度必须在-90到90之间";
        }
        if (longitude != null && (longitude < -180 || longitude > 180)) {
            return "经度必须在-180到180之间";
        }
        
        return null;
    }
    
    /**
     * 验证设备类型是否有效
     */
    public boolean validateDeviceType() {
        return deviceType != null;
    }
    
    /**
     * 验证坐标是否完整
     */
    public boolean hasCompleteCoordinates() {
        return latitude != null && longitude != null;
    }
    
    /**
     * 检查是否为物联网设备
     */
    public boolean isIoTDevice() {
        if (deviceType == null) {
            return false;
        }
        
        return deviceType == DeviceType.SENSOR_TAG || 
               deviceType == DeviceType.GATEWAY ||
               deviceType == DeviceType.CONTROLLER ||
               deviceType == DeviceType.ACTUATOR;
    }
    
    /**
     * 检查是否为网关设备
     */
    public boolean isGatewayDevice() {
        return deviceType == DeviceType.GATEWAY;
    }
    
    /**
     * 检查是否为传感器设备
     */
    public boolean isSensorDevice() {
        return deviceType == DeviceType.SENSOR_TAG;
    }
    
    /**
     * 获取设备初始化配置
     */
    public String getDefaultConfigJson() {
        if (configJson != null && !configJson.trim().isEmpty()) {
            return configJson;
        }
        
        // 为不同类型的设备提供默认配置
        if (deviceType == DeviceType.SENSOR_TAG) {
            return "{\"data_interval\": 60, \"battery_saving\": true, \"signal_threshold\": -85}";
        } else if (deviceType == DeviceType.GATEWAY) {
            return "{\"mqtt_port\": 1883, \"max_devices\": 50, \"heartbeat_interval\": 300}";
        } else {
            return "{\"basic_config\": {}}";
        }
    }
    
    /**
     * 获取设备描述信息
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append(" (").append(serialNumber).append(")");
        
        if (model != null && !model.isEmpty()) {
            desc.append(" - ").append(model);
        }
        
        if (manufacturer != null && !manufacturer.isEmpty()) {
            desc.append(" - ").append(manufacturer);
        }
        
        return desc.toString();
    }
}