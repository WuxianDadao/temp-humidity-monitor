package com.iot.temphumidity.entity.tdengine;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TDengine传感器数据实体
 * 对应 sensor_data 超表
 * 注意: 这个类只是数据模型，不用于ORM映射
 * TDengine使用JdbcTemplate进行数据访问
 */
@Data
public class SensorDataTD {
    
    /**
     * 时间戳 (主键) - 精确时间戳 (纳秒精度)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime timestamp;
    
    /**
     * 温度 (°C)，精度0.1°C
     */

    private Float temperature;
    
    /**
     * 湿度 (%)，精度0.1%
     */

    private Float humidity;
    
    /**
     * 大气压 (hPa)，精度0.1hPa
     */

    private Float pressure;
    
    /**
     * 电池电量 (%)，精度0.1%
     */

    private Float battery;
    
    /**
     * 信号强度 (dBm)
     */

    private Integer rssi;
    
    /**
     * 电压 (V)，精度0.01V
     */

    private Float voltage;
    
    /**
     * 内部温度 (°C)，设备内部测温
     */

    private Float temperatureInternal;
    
    /**
     * 内部湿度 (%)，设备内部湿度
     */

    private Float humidityInternal;
    
    /**
     * 状态码 (0-255)
     */

    private Short statusCode;
    
    /**
     * 错误标志位 (位掩码)
     */

    private Integer errorFlags;
    
    /**
     * 数据质量 (0-100)
     */

    private Byte dataQuality;
    
    /**
     * 采样率 (秒)
     */

    private Integer sampleRate;
    
    /**
     * 固件CRC校验
     */

    private Integer firmwareCrc;
    
    /**
     * 原始数据 (最大64字节)
     */

    private byte[] rawData;
    
    // ========== 标签字段 (TAG) ==========
    
    /**
     * 设备唯一标识 (4G网关ICCID)
     */

    private String deviceId;
    
    /**
     * 设备类型 (temperature_humidity_sensor)
     */

    private String deviceType;
    
    /**
     * 网关设备ID (4G网关)
     */

    private String gatewayId;
    
    /**
     * 安装位置 (中文描述)
     */

    private String location;
    
    /**
     * 房间编号
     */

    private String roomNumber;
    
    /**
     * 建筑名称
     */

    private String building;
    
    /**
     * 楼层
     */

    private String floor;
    
    /**
     * 区域划分
     */

    private String zone;
    
    /**
     * 纬度 (WGS84)
     */

    private Double latitude;
    
    /**
     * 经度 (WGS84)
     */

    private Double longitude;
    
    /**
     * 海拔高度 (米)
     */

    private Float altitude;
    
    /**
     * 制造商
     */

    private String manufacturer;
    
    /**
     * 型号
     */

    private String model;
    
    /**
     * 传感器类型
     */

    private String sensorType;
    
    /**
     * 序列号
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
     * 校准日期
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime calibrationDate;
    
    /**
     * 下次校准日期
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime calibrationDueDate;
    
    /**
     * 安装日期
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime installationDate;
    
    /**
     * 维护间隔 (天)
     */

    private Integer maintenanceInterval;
    
    /**
     * 设备状态 (active/inactive/maintenance/error)
     */

    private String deviceStatus;
    
    /**
     * 电源类型 (battery/ac/solar)
     */

    private String powerSource;
    
    /**
     * 网络类型 (4G/WiFi/LoRa/NB-IoT)
     */

    private String networkType;
    
    /**
     * 采样间隔 (秒)
     */

    private Integer samplingInterval;
    
    /**
     * 上报间隔 (秒)
     */

    private Integer reportingInterval;
    
    /**
     * 报警阈值配置 (JSON格式)
     */

    private String alarmThresholds;
    
    /**
     * 自定义元数据 (JSON格式)
     */

    private String customMetadata;
    
    /**
     * 最后维护日期
     */

    private LocalDateTime lastMaintenanceDate;
    
    /**
     * 下次维护日期
     */

    private LocalDateTime nextMaintenanceDate;
    
    /**
     * 安装位置
     */

    private String installationLocation;
    
    /**
     * 安装人员
     */

    private String installationPerson;
    
    /**
     * 维护联系人
     */

    private String maintenanceContact;
    

    
    /**
     * 校准间隔 (单位: 天)
     */

    private Integer calibrationInterval;
    
    /**
     * 保修期 (单位: 月)
     */

    private Integer warrantyPeriod;
    
    /**
     * 购买日期 (字符串格式)
     */

    private String purchaseDate;
    
    /**
     * 供应商
     */

    private String supplier;
    
    /**
     * 购买价格
     */

    private BigDecimal purchasePrice;
    
    /**
     * 折旧率
     */

    private BigDecimal depreciationRate;
    
    /**
     * 资产编号
     */

    private String assetNumber;
    
    /**
     * 部门
     */

    private String department;
    
    /**
     * 备注信息
     */

    private String notes;
    
    /**
     * 自定义字段1
     */

    private String customField1;
    
    /**
     * 自定义字段2
     */

    private String customField2;
    
    /**
     * 自定义字段3
     */

    private String customField3;
    
    /**
     * 自定义字段4
     */

    private String customField4;
    
    /**
     * 自定义字段5
     */

    private String customField5;
    
    /**
     * 负责人
     */

    private String responsiblePerson;
    
    /**
     * 联系电话
     */

    private String contactPhone;
    
    /**
     * 联系邮箱
     */

    private String contactEmail;
    
    // ========== 业务方法 ==========
    
    /**
     * 检查数据是否有效
     */
    public boolean isValid() {
        return temperature != null && humidity != null && dataQuality != null && dataQuality > 50;
    }
    
    /**
     * 获取数据质量级别
     */
    public String getQualityLevel() {
        if (dataQuality == null) return "UNKNOWN";
        if (dataQuality >= 90) return "EXCELLENT";
        if (dataQuality >= 70) return "GOOD";
        if (dataQuality >= 50) return "FAIR";
        return "POOR";
    }
    
    /**
     * 检查是否需要报警
     */
    public boolean needsAlarm(Double tempThresholdHigh, Double tempThresholdLow, 
                              Double humidityThresholdHigh, Double humidityThresholdLow) {
        if (!isValid()) return false;
        
        boolean tempAlarm = (tempThresholdHigh != null && temperature > tempThresholdHigh) ||
                           (tempThresholdLow != null && temperature < tempThresholdLow);
        
        boolean humidityAlarm = (humidityThresholdHigh != null && humidity > humidityThresholdHigh) ||
                               (humidityThresholdLow != null && humidity < humidityThresholdLow);
        
        return tempAlarm || humidityAlarm;
    }
    
    /**
     * 获取报警类型
     */
    public String getAlarmType(Double tempThresholdHigh, Double tempThresholdLow, 
                               Double humidityThresholdHigh, Double humidityThresholdLow) {
        if (!needsAlarm(tempThresholdHigh, tempThresholdLow, humidityThresholdHigh, humidityThresholdLow)) {
            return null;
        }
        
        StringBuilder alarmTypes = new StringBuilder();
        
        if (tempThresholdHigh != null && temperature > tempThresholdHigh) {
            alarmTypes.append("HIGH_TEMP,");
        }
        if (tempThresholdLow != null && temperature < tempThresholdLow) {
            alarmTypes.append("LOW_TEMP,");
        }
        if (humidityThresholdHigh != null && humidity > humidityThresholdHigh) {
            alarmTypes.append("HIGH_HUMIDITY,");
        }
        if (humidityThresholdLow != null && humidity < humidityThresholdLow) {
            alarmTypes.append("LOW_HUMIDITY,");
        }
        
        if (alarmTypes.length() > 0) {
            alarmTypes.setLength(alarmTypes.length() - 1); // 移除最后一个逗号
        }
        
        return alarmTypes.toString();
    }
    
    /**
     * 转换为JSON字符串（用于日志和调试）
     */
    public String toJsonString() {
        return String.format(
            "{\"ts\":\"%s\",\"temp\":%.2f,\"hum\":%.2f,\"device\":\"%s\",\"quality\":%d}",
            timestamp, temperature, humidity, deviceId, dataQuality
        );
    }
}