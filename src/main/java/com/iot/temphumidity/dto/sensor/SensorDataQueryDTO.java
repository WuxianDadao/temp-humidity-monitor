package com.iot.temphumidity.dto.sensor;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 传感器数据查询参数DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "传感器数据查询参数")
public class SensorDataQueryDTO {
    
    /**
     * 传感器ID列表（可空，查询所有传感器）
     */
    @Schema(description = "传感器ID列表，为空则查询所有传感器", example = "[1001, 1002, 1003]")
    private Long[] sensorIds;
    
    /**
     * 网关ID列表（可空）
     */
    @Schema(description = "网关ID列表", example = "[2001, 2002]")
    private Long[] gatewayIds;
    
    /**
     * 设备类型
     */
    @Schema(description = "设备类型", example = "temperature_humidity")
    private String deviceType;
    
    /**
     * 位置/区域
     */
    @Schema(description = "位置/区域", example = "机房A")
    private String location;
    
    /**
     * 开始时间
     */
    @Schema(description = "开始时间", example = "2024-01-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @Schema(description = "结束时间", example = "2024-01-01 23:59:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    /**
     * 查询时间范围（单位：小时）
     */
    @Schema(description = "查询时间范围（小时），优先级高于startTime/endTime", example = "24")
    private Integer timeRangeHours;
    
    /**
     * 查询类型
     */
    @Schema(description = "查询类型", example = "HISTORY", allowableValues = {"LATEST", "HISTORY", "REALTIME", "ALARM", "STATISTICS"})
    private QueryType queryType;
    
    /**
     * 统计类型（当queryType为STATISTICS时有效）
     */
    @Schema(description = "统计类型", example = "DAILY", allowableValues = {"HOURLY", "DAILY", "WEEKLY", "MONTHLY", "CUSTOM"})
    private StatisticsType statisticsType;
    
    /**
     * 数据质量过滤
     */
    @Schema(description = "数据质量过滤", example = "VALID_ONLY", allowableValues = {"ALL", "VALID_ONLY", "INVALID_ONLY"})
    private DataQualityFilter dataQualityFilter;
    
    /**
     * 报警级别过滤
     */
    @Schema(description = "报警级别过滤", example = "CRITICAL_AND_WARNING", allowableValues = {"ALL", "CRITICAL_ONLY", "WARNING_ONLY", "CRITICAL_AND_WARNING", "NONE"})
    private AlarmLevelFilter alarmLevelFilter;
    
    /**
     * 设备状态过滤
     */
    @Schema(description = "设备状态过滤", example = "ONLINE", allowableValues = {"ALL", "ONLINE", "OFFLINE", "MALFUNCTION"})
    private DeviceStatusFilter deviceStatusFilter;
    
    /**
     * 分页参数 - 页码（从1开始）
     */
    @Schema(description = "页码（从1开始）", example = "1")
    private Integer pageNumber;
    
    /**
     * 分页参数 - 每页大小
     */
    @Schema(description = "每页大小", example = "100")
    private Integer pageSize;
    
    /**
     * 排序字段
     */
    @Schema(description = "排序字段", example = "timestamp", allowableValues = {"timestamp", "temperature", "humidity", "battery", "rssi"})
    private String sortField;
    
    /**
     * 排序方向
     */
    @Schema(description = "排序方向", example = "DESC", allowableValues = {"ASC", "DESC"})
    private SortDirection sortDirection;
    
    /**
     * 是否包含原始数据
     */
    @Schema(description = "是否包含原始数据", example = "false")
    private Boolean includeRawData;
    
    /**
     * 是否包含元数据
     */
    @Schema(description = "是否包含元数据", example = "true")
    private Boolean includeMetadata;
    
    /**
     * 是否包含统计信息
     */
    @Schema(description = "是否包含统计信息", example = "true")
    private Boolean includeStatistics;
    
    /**
     * 是否包含报警信息
     */
    @Schema(description = "是否包含报警信息", example = "true")
    private Boolean includeAlarms;
    
    /**
     * 是否包含设备信息
     */
    @Schema(description = "是否包含设备信息", example = "true")
    private Boolean includeDeviceInfo;
    
    /**
     * 数据格式
     */
    @Schema(description = "数据格式", example = "JSON", allowableValues = {"JSON", "CSV", "EXCEL"})
    private DataFormat dataFormat;
    
    /**
     * 查询时间戳（用于请求跟踪）
     */
    @Schema(description = "查询时间戳（请求跟踪）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime queryTimestamp;
    
    /**
     * 查询超时时间（秒）
     */
    @Schema(description = "查询超时时间（秒）", example = "30")
    private Integer timeoutSeconds;
    
    /**
     * 查询类型枚举
     */
    public enum QueryType {
        LATEST,      // 最新数据
        HISTORY,     // 历史数据
        REALTIME,    // 实时数据
        ALARM,       // 报警数据
        STATISTICS   // 统计数据
    }
    
    /**
     * 统计类型枚举
     */
    public enum StatisticsType {
        HOURLY,      // 小时统计
        DAILY,       // 日统计
        WEEKLY,      // 周统计
        MONTHLY,     // 月统计
        CUSTOM       // 自定义统计
    }
    
    /**
     * 数据质量过滤枚举
     */
    public enum DataQualityFilter {
        ALL,         // 所有数据
        VALID_ONLY,  // 仅有效数据
        INVALID_ONLY // 仅无效数据
    }
    
    /**
     * 报警级别过滤枚举
     */
    public enum AlarmLevelFilter {
        ALL,                    // 所有报警
        CRITICAL_ONLY,          // 仅严重报警
        WARNING_ONLY,           // 仅警告报警
        CRITICAL_AND_WARNING,   // 严重和警告报警
        NONE                    // 无报警
    }
    
    /**
     * 设备状态过滤枚举
     */
    public enum DeviceStatusFilter {
        ALL,         // 所有设备
        ONLINE,      // 仅在线设备
        OFFLINE,     // 仅离线设备
        MALFUNCTION  // 仅故障设备
    }
    
    /**
     * 排序方向枚举
     */
    public enum SortDirection {
        ASC,   // 升序
        DESC   // 降序
    }
    
    /**
     * 数据格式枚举
     */
    public enum DataFormat {
        JSON,   // JSON格式
        CSV,    // CSV格式
        EXCEL   // Excel格式
    }
    
    /**
     * 构建默认查询（最近24小时数据）
     */
    public static SensorDataQueryDTO defaultQuery() {
        return SensorDataQueryDTO.builder()
                .startTime(LocalDateTime.now().minusHours(24))
                .endTime(LocalDateTime.now())
                .queryType(QueryType.HISTORY)
                .dataQualityFilter(DataQualityFilter.VALID_ONLY)
                .pageNumber(1)
                .pageSize(100)
                .sortField("timestamp")
                .sortDirection(SortDirection.DESC)
                .includeMetadata(true)
                .includeStatistics(false)
                .includeAlarms(false)
                .includeDeviceInfo(false)
                .dataFormat(DataFormat.JSON)
                .queryTimestamp(LocalDateTime.now())
                .timeoutSeconds(30)
                .build();
    }
    
    /**
     * 构建实时数据查询
     */
    public static SensorDataQueryDTO realtimeQuery() {
        return SensorDataQueryDTO.builder()
                .startTime(LocalDateTime.now().minusMinutes(5))
                .endTime(LocalDateTime.now())
                .queryType(QueryType.REALTIME)
                .dataQualityFilter(DataQualityFilter.VALID_ONLY)
                .pageNumber(1)
                .pageSize(50)
                .sortField("timestamp")
                .sortDirection(SortDirection.DESC)
                .includeMetadata(true)
                .includeStatistics(false)
                .includeAlarms(true)
                .includeDeviceInfo(true)
                .dataFormat(DataFormat.JSON)
                .queryTimestamp(LocalDateTime.now())
                .timeoutSeconds(10)
                .build();
    }
    
    /**
     * 构建报警数据查询
     */
    public static SensorDataQueryDTO alarmQuery() {
        return SensorDataQueryDTO.builder()
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(LocalDateTime.now())
                .queryType(QueryType.ALARM)
                .alarmLevelFilter(AlarmLevelFilter.CRITICAL_AND_WARNING)
                .pageNumber(1)
                .pageSize(100)
                .sortField("timestamp")
                .sortDirection(SortDirection.DESC)
                .includeMetadata(true)
                .includeStatistics(false)
                .includeAlarms(true)
                .includeDeviceInfo(true)
                .dataFormat(DataFormat.JSON)
                .queryTimestamp(LocalDateTime.now())
                .timeoutSeconds(30)
                .build();
    }
}