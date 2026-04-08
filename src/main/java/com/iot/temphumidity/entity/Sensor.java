package com.iot.temphumidity.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sensorId;
    
    private String deviceId;
    private String sensorName;
    private String sensorType;
    private String location;
    private String description;
    
    private Double latitude;
    private Double longitude;
    private Double altitude;
    
    @Column(updatable = false)
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
