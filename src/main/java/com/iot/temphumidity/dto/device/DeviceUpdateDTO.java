package com.iot.temphumidity.dto.device;

import com.iot.temphumidity.entity.Device;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 设备更新数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUpdateDTO {
    
    /**
     * 设备名称
     */
    @Size(min = 1, max = 128, message = "设备名称长度必须在1-128个字符之间")
    private String name;
    
    /**
     * 设备名称（别名，用于兼容性）
     */
    public String getDeviceName() {
        return name;
    }
    
    /**
     * 设置设备名称（别名，用于兼容性）
     */
    public void setDeviceName(String deviceName) {
        this.name = deviceName;
    }
    
    /**
     * 组织ID
     */
    private Long organizationId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 设备类型
     */
    @Size(max = 50, message = "设备类型长度不能超过50个字符")
    private String deviceType;
    
    /**
     * 设备描述
     */
    @Size(max = 500, message = "设备描述长度不能超过500个字符")
    private String description;
    
    /**
     * 设备位置
     */
    @Size(max = 255, message = "设备位置长度不能超过255个字符")
    private String location;
    
    /**
     * 所属网关ID
     */
    private Long gatewayId;
    
    /**
     * 所属网关名称
     */
    private String gatewayName;
    
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
     * MQTT客户端ID
     */
    @Size(max = 128, message = "MQTT客户端ID长度不能超过128个字符")
    private String mqttClientId;
    
    /**
     * MQTT主题前缀
     */
    @Size(max = 128, message = "MQTT主题前缀长度不能超过128个字符")
    private String mqttTopicPrefix;
    
    /**
     * 上报间隔（秒）
     */
    private Integer reportInterval;
    
    /**
     * 应用更新到实体
     */
    public void applyToEntity(Device entity) {
        if (entity == null) {
            return;
        }
        
        if (this.name != null) {
            entity.setDeviceName(this.name);
        }
        if (this.location != null) {
            entity.setLocation(this.location);
        }
        if (this.gatewayId != null) {
            // 注意：Device实体类没有直接setGatewayId方法
            // 需要通过Gateway对象设置
            // 这里我们暂时不处理，因为需要GatewayRepository
        }
        if (this.configJson != null) {
            // Device实体类没有configJson字段，但有alarm_config字段
            // 这里应该将configJson存储到alarm_config字段
            try {
                // 检查是否可以通过反射设置
                entity.setAlarmConfig(this.configJson);
            } catch (Exception e) {
                // 如果setAlarmConfig不存在，使用metadata字段
                entity.setMetadata("{\"config\":" + this.configJson + "}");
            }
        }
        if (this.metadataJson != null) {
            entity.setMetadata(this.metadataJson);
        }
        if (this.latitude != null) {
            entity.setLatitude(this.latitude);
        }
        if (this.longitude != null) {
            entity.setLongitude(this.longitude);
        }
        if (this.timezone != null) {
            entity.setTimezone(this.timezone);
        }
        if (this.enabled != null) {
            entity.setAlarmEnabled(this.enabled);
        }
        if (this.remark != null) {
            entity.setRemark(this.remark);
        }
        if (this.tags != null) {
            entity.setTags(this.tags);
        }
    }
    
    /**
     * 验证坐标
     */
    public boolean validateCoordinates() {
        if (latitude != null && (latitude < -90 || latitude > 90)) {
            return false;
        }
        if (longitude != null && (longitude < -180 || longitude > 180)) {
            return false;
        }
        return true;
    }
    
    /**
     * 检查是否有任何字段被更新
     */
    public boolean hasUpdates() {
        return name != null || 
               location != null || 
               gatewayId != null || 
               configJson != null ||
               metadataJson != null ||
               latitude != null ||
               longitude != null ||
               timezone != null ||
               enabled != null ||
               remark != null ||
               tags != null;
    }
    
    /**
     * 获取更新摘要
     */
    public String getUpdateSummary() {
        StringBuilder summary = new StringBuilder();
        
        if (name != null) summary.append("设备名称, ");
        if (location != null) summary.append("设备位置, ");
        if (gatewayId != null) summary.append("所属网关, ");
        if (configJson != null) summary.append("设备配置, ");
        if (metadataJson != null) summary.append("设备元数据, ");
        if (latitude != null || longitude != null) summary.append("地理位置, ");
        if (timezone != null) summary.append("时区, ");
        if (enabled != null) summary.append("启用状态, ");
        if (remark != null) summary.append("备注, ");
        if (tags != null) summary.append("标签, ");
        
        if (summary.length() > 0) {
            summary.setLength(summary.length() - 2); // 移除最后的逗号和空格
        }
        
        return summary.toString();
    }
    
    /**
     * 检查是否更新了位置信息
     */
    public boolean isLocationUpdated() {
        return location != null || latitude != null || longitude != null;
    }
    
    /**
     * 检查是否更新了设备配置
     */
    public boolean isConfigUpdated() {
        return configJson != null || metadataJson != null;
    }
    
    /**
     * 检查是否更新了设备状态
     */
    public boolean isStatusUpdated() {
        return enabled != null;
    }
    
    /**
     * 获取位置更新信息
     */
    public String getLocationUpdateInfo() {
        StringBuilder info = new StringBuilder();
        
        if (location != null) {
            info.append("位置: ").append(location).append(", ");
        }
        if (latitude != null) {
            info.append("纬度: ").append(latitude).append(", ");
        }
        if (longitude != null) {
            info.append("经度: ").append(longitude).append(", ");
        }
        
        if (info.length() > 0) {
            info.setLength(info.length() - 2); // 移除最后的逗号和空格
        }
        
        return info.toString();
    }
    
    /**
     * 验证JSON配置
     */
    public boolean validateJsonConfig() {
        if (configJson != null) {
            try {
                // 简单验证是否为有效JSON
                if (!configJson.trim().startsWith("{") && !configJson.trim().startsWith("[")) {
                    return false;
                }
                // TODO: 可以使用Jackson或Gson进行更严格的验证
            } catch (Exception e) {
                return false;
            }
        }
        
        if (metadataJson != null) {
            try {
                if (!metadataJson.trim().startsWith("{") && !metadataJson.trim().startsWith("[")) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 获取验证错误信息
     */
    public String getValidationError() {
        if (!validateCoordinates()) {
            return "经纬度坐标无效";
        }
        if (!validateJsonConfig()) {
            return "JSON配置格式无效";
        }
        return null;
    }
    
    /**
     * 获取设备型号
     */
    public String getModel() {
        return null; // DeviceUpdateDTO中没有model字段
    }
    
    /**
     * 获取序列号
     */
    public String getSerialNumber() {
        return null; // DeviceUpdateDTO中没有serialNumber字段
    }
}