package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.entity.BaseEntity;
import com.iot.temphumidity.enums.GatewayStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 网关实体类 - PostgreSQL存储的业务实体
 * 
 * 网关是物联网系统的核心设备，通过4G网络连接，管理多个传感器标签
 */
@Entity
@Table(name = "gateway", schema = "iot")
@Getter
@Setter
public class GatewayEntity extends BaseEntity {
    
    /**
     * ICCID (集成电路卡识别码) - 唯一标识符
     * SIM卡的唯一识别码，20位数字，用于4G网关身份认证
     */
    @Column(name = "iccid", nullable = false, unique = true, length = 20)
    private String iccid;
    
    /**
     * 网关名称
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /**
     * 网关型号
     */
    @Column(name = "model", length = 50)
    private String model;
    
    /**
     * 网关制造商
     */
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;
    
    /**
     * 软件版本
     */
    @Column(name = "software_version", length = 50)
    private String softwareVersion;
    
    /**
     * 硬件版本
     */
    @Column(name = "hardware_version", length = 50)
    private String hardwareVersion;
    
    /**
     * 网关状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private GatewayStatus status = GatewayStatus.OFFLINE;
    
    /**
     * 最后在线时间
     */
    @Column(name = "last_online_time")
    private LocalDateTime lastOnlineTime;
    
    /**
     * 最后上报数据时间
     */
    @Column(name = "last_data_report_time")
    private LocalDateTime lastDataReportTime;
    
    /**
     * 所属用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 安装位置
     */
    @Column(name = "location", length = 200)
    private String location;
    
    /**
     * 安装时间
     */
    @Column(name = "installation_time")
    private LocalDateTime installationTime;
    
    /**
     * 备注信息
     */
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    /**
     * 网关密钥（用于MQTT认证）
     */
    @Column(name = "gateway_key", length = 100)
    private String gatewayKey;
    
    /**
     * MQTT客户端ID
     */
    @Column(name = "mqtt_client_id", length = 100)
    private String mqttClientId;
    
    /**
     * MQTT主题前缀
     */
    @Column(name = "mqtt_topic_prefix", length = 100)
    private String mqttTopicPrefix;
    
    /**
     * 上报间隔（秒）
     */
    @Column(name = "report_interval_seconds", nullable = false)
    private Integer reportIntervalSeconds = 60;
    
    /**
     * 心跳间隔（秒）
     */
    @Column(name = "heartbeat_interval_seconds", nullable = false)
    private Integer heartbeatIntervalSeconds = 300;
    
    /**
     * 数据保留天数
     */
    @Column(name = "data_retention_days", nullable = false)
    private Integer dataRetentionDays = 90;
    
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
     * 网络运营商
     */
    @Column(name = "network_operator", length = 50)
    private String networkOperator;
    
    /**
     * 信号强度（最后上报）
     */
    @Column(name = "rssi")
    private Integer rssi;
    
    /**
     * 电池电量（最后上报）
     */
    @Column(name = "battery_level")
    private Double batteryLevel;
    
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
     * 所属用户（多对一关系）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;
    
    /**
     * 管理的传感器标签（一对多关系）
     */
    @OneToMany(mappedBy = "gateway", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SensorTagEntity> sensorTags = new HashSet<>();
    
    /**
     * 关联的设备（一对多关系）
     */
    @OneToMany(mappedBy = "gateway", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DeviceEntity> devices = new HashSet<>();
    
    /**
     * 构造函数
     */
    public GatewayEntity() {
        super();
    }
    
    /**
     * 带ICCID和名称的构造函数
     */
    public GatewayEntity(String iccid, String name, Long userId) {
        this.iccid = iccid;
        this.name = name;
        this.userId = userId;
        this.status = GatewayStatus.REGISTERED;
    }
    
    /**
     * 网关上线
     */
    public void goOnline() {
        this.status = GatewayStatus.ONLINE;
        this.lastOnlineTime = LocalDateTime.now();
    }
    
    /**
     * 网关下线
     */
    public void goOffline() {
        this.status = GatewayStatus.OFFLINE;
    }
    
    /**
     * 更新上报数据时间
     */
    public void updateDataReportTime() {
        this.lastDataReportTime = LocalDateTime.now();
    }
    
    /**
     * 获取网关简要信息
     */
    public String getGatewayInfo() {
        return String.format("网关[%s] %s - %s", iccid, name, status.getDisplayName());
    }
    
    /**
     * 检查是否在线
     */
    public boolean isOnline() {
        return GatewayStatus.ONLINE.equals(this.status);
    }
    
    /**
     * 检查是否超时（最后在线时间超过心跳间隔的2倍）
     */
    public boolean isTimeout() {
        if (lastOnlineTime == null) return false;
        return LocalDateTime.now().minusSeconds(heartbeatIntervalSeconds * 2L)
                .isAfter(lastOnlineTime);
    }
}