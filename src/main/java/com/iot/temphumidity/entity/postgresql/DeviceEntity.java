package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 设备实体类 - 物理设备信息
 */
@Entity
@Table(name = "device", schema = "iot")
@Getter
@Setter
public class DeviceEntity extends BaseEntity {
    
    @Column(name = "device_sn", nullable = false, unique = true, length = 50)
    private String deviceSn;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "model", length = 50)
    private String model;
    
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;
    
    @Column(name = "gateway_id", nullable = false)
    private Long gatewayId;
    
    @Column(name = "location", length = 200)
    private String location;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "installation_time")
    private LocalDateTime installationTime;
    
    @Column(name = "remarks", length = 1000)
    private String remarks;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gateway_id", referencedColumnName = "id", insertable = false, updatable = false)
    private GatewayEntity gateway;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
    
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private Set<SensorTagEntity> sensorTags = new HashSet<>();
    
    public DeviceEntity() {
        super();
    }
    
    public DeviceEntity(String deviceSn, String name, Long gatewayId) {
        this.deviceSn = deviceSn;
        this.name = name;
        this.gatewayId = gatewayId;
    }
    
    public String getDeviceInfo() {
        return String.format("设备[%s] %s - %s", deviceSn, name, model != null ? model : "未知型号");
    }
}