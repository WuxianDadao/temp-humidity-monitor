package com.iot.temphumidity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 传感器数据批量传输DTO
 * 用于网关批量上报传感器数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataBatchDTO {
    private Long gatewayId;
    private String gatewaySerialNumber;
    private LocalDateTime reportTime;
    private List<SensorDataItem> sensorDataList;
    
    // 统计数据字段
    private Integer sensorCount;
    private Double avgTemperature;
    private Double avgHumidity;
    private Double avgBattery;
    private Double minTemperature;
    private Double maxTemperature;
    private Double minHumidity;
    private Double maxHumidity;
    private Double minBattery;
    private Double maxBattery;
    
    // 网络质量字段
    private Integer avgRssi;
    private Integer minRssi;
    private Integer maxRssi;
    
    // 地理位置统计
    private Double avgLongitude;
    private Double avgLatitude;
    private String locationRegion;
    
    // 数据质量指标
    private Integer dataQualityScore;
    private Integer invalidDataCount;
    private Integer missingDataCount;
    private Integer duplicateDataCount;
    
    // 扩展字段
    private String batchId;
    private String version;
    private String compression;
    private String checksum;
    private Boolean processed;
    private LocalDateTime processedTime;
    private String processingError;
    
    /**
     * 获取传感器数量
     */
    public int getSensorDataCount() {
        return sensorDataList != null ? sensorDataList.size() : 0;
    }
    
    /**
     * 检查是否为空批次
     */
    public boolean isEmpty() {
        return sensorDataList == null || sensorDataList.isEmpty();
    }
    
    /**
     * 检查是否有有效数据
     */
    public boolean hasValidData() {
        if (isEmpty()) return false;
        return sensorDataList.stream()
            .anyMatch(item -> item != null && item.isValid());
    }
    
    /**
     * 获取批次标识
     */
    public String getBatchIdentifier() {
        if (batchId != null) {
            return batchId;
        }
        return String.format("%s_%s", gatewaySerialNumber, reportTime != null ? reportTime.toString() : "unknown");
    }
    
    // Setter方法
    public void setSensorCount(Integer sensorCount) {
        this.sensorCount = sensorCount;
    }
    
    public void setAvgTemperature(Double avgTemperature) {
        this.avgTemperature = avgTemperature;
    }
    
    public void setAvgHumidity(Double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }
    
    public void setAvgBattery(Double avgBattery) {
        this.avgBattery = avgBattery;
    }
    
    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }
    
    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
    
    public void setMinHumidity(Double minHumidity) {
        this.minHumidity = minHumidity;
    }
    
    public void setMaxHumidity(Double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }
    
    public void setMinBattery(Double minBattery) {
        this.minBattery = minBattery;
    }
    
    public void setMaxBattery(Double maxBattery) {
        this.maxBattery = maxBattery;
    }
    
    public void setAvgRssi(Integer avgRssi) {
        this.avgRssi = avgRssi;
    }
    
    public void setMinRssi(Integer minRssi) {
        this.minRssi = minRssi;
    }
    
    public void setMaxRssi(Integer maxRssi) {
        this.maxRssi = maxRssi;
    }
    
    public void setAvgLongitude(Double avgLongitude) {
        this.avgLongitude = avgLongitude;
    }
    
    public void setAvgLatitude(Double avgLatitude) {
        this.avgLatitude = avgLatitude;
    }
    
    public void setLocationRegion(String locationRegion) {
        this.locationRegion = locationRegion;
    }
    
    public void setDataQualityScore(Integer dataQualityScore) {
        this.dataQualityScore = dataQualityScore;
    }
    
    public void setInvalidDataCount(Integer invalidDataCount) {
        this.invalidDataCount = invalidDataCount;
    }
    
    public void setMissingDataCount(Integer missingDataCount) {
        this.missingDataCount = missingDataCount;
    }
    
    public void setDuplicateDataCount(Integer duplicateDataCount) {
        this.duplicateDataCount = duplicateDataCount;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SensorDataItem {
        private Long sensorTagId;
        private String sensorSerialNumber;
        private LocalDateTime timestamp;
        private Double temperature;
        private Double humidity;
        private Double battery;
        private Integer rssi;
        private Double longitude;
        private Double latitude;
        private Integer dataQuality;
        private String rawData;
        
        // 扩展字段
        private String tagType;
        private String tagVersion;
        private String measurementUnit;
        private Double temperatureRaw;
        private Double humidityRaw;
        private Integer calibrationStatus;
        private String calibrationDate;
        private String sensorStatus;
        private String sensorErrorCode;
        
        /**
         * 检查数据是否有效
         */
        public boolean isValid() {
            return timestamp != null && 
                   (temperature != null || humidity != null) &&
                   dataQuality != null && dataQuality >= 0 && dataQuality <= 100;
        }
        
        /**
         * 检查是否有温度数据
         */
        public boolean hasTemperature() {
            return temperature != null;
        }
        
        /**
         * 检查是否有湿度数据
         */
        public boolean hasHumidity() {
            return humidity != null;
        }
        
        /**
         * 检查是否有位置数据
         */
        public boolean hasLocation() {
            return longitude != null && latitude != null;
        }
        
        /**
         * 获取位置坐标
         */
        public String getLocationCoordinates() {
            if (hasLocation()) {
                return String.format("%.6f,%.6f", latitude, longitude);
            }
            return null;
        }
        
        /**
         * 获取数据质量描述
         */
        public String getDataQualityDescription() {
            if (dataQuality == null) return "未知";
            if (dataQuality >= 90) return "优秀";
            if (dataQuality >= 70) return "良好";
            if (dataQuality >= 50) return "一般";
            if (dataQuality >= 30) return "较差";
            return "极差";
        }
        
        /**
         * 获取传感器状态描述
         */
        public String getSensorStatusDescription() {
            if (sensorStatus == null) return "未知";
            switch (sensorStatus) {
                case "normal": return "正常";
                case "warning": return "警告";
                case "error": return "错误";
                case "offline": return "离线";
                case "calibrating": return "校准中";
                case "maintenance": return "维护中";
                default: return sensorStatus;
            }
        }
    }
}