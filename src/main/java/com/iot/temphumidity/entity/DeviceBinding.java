package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 设备绑定实体类
 * 对应PostgreSQL中的device_bindings表
 */
@Entity
@Table(name = "device_bindings",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_device_bindings_unique", columnNames = {"gateway_id", "sensor_id", "binding_type"})
       },
       indexes = {
           @Index(name = "idx_device_bindings_gateway", columnList = "gateway_id"),
           @Index(name = "idx_device_bindings_sensor", columnList = "sensor_id")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DeviceBinding extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "gateway_id", nullable = false)
    private Long gatewayId;  // 网关设备ID
    
    @Column(name = "sensor_id", nullable = false)
    private Long sensorId;  // 传感器ID
    
    @Column(name = "binding_type", length = 16)
    @Builder.Default
    private String bindingType = "physical";  // 绑定类型 (physical, virtual, logical)
    
    @Column(name = "binding_time")
    private LocalDateTime bindingTime;  // 绑定时间
    
    @Column(name = "unbind_time")
    private LocalDateTime unbindTime;  // 解绑时间
    
    @Column(name = "channel")
    private Integer channel;  // 通信通道
    
    @Column(name = "protocol", length = 16)
    private String protocol;  // 通信协议
    
    @Column(name = "signal_strength")
    private Integer signalStrength;  // 信号强度
    
    @Column(name = "last_communication")
    private LocalDateTime lastCommunication;  // 最后通信时间
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;  // 备注
    
    // 绑定类型枚举
    public enum BindingType {
        PHYSICAL("physical"),
        VIRTUAL("virtual"),
        LOGICAL("logical");
        
        private final String value;
        
        BindingType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    // 检查绑定是否活跃
    public boolean isActive() {
        return unbindTime == null || unbindTime.isAfter(LocalDateTime.now());
    }
    
    // 获取绑定持续时间（分钟）
    public long getBindingDurationMinutes() {
        if (bindingTime == null) return 0;
        
        LocalDateTime endTime = unbindTime != null ? unbindTime : LocalDateTime.now();
        return java.time.Duration.between(bindingTime, endTime).toMinutes();
    }
    
    // 更新最后通信时间
    public void updateLastCommunication() {
        this.lastCommunication = LocalDateTime.now();
    }
    
    // 预持久化回调
    @PrePersist
    protected void onCreate() {
        if (bindingTime == null) {
            bindingTime = LocalDateTime.now();
        }
    }
}