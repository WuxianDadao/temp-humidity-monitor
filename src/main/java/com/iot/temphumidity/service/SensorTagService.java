package com.iot.temphumidity.service;

import com.iot.temphumidity.dto.sensor.SensorTagCreateDTO;
import com.iot.temphumidity.dto.sensor.SensorTagDTO;
import com.iot.temphumidity.dto.sensor.SensorTagUpdateDTO;
import com.iot.temphumidity.dto.sensor.SensorDataDTO;
import com.iot.temphumidity.dto.sensor.SensorDataQueryDTO;
import com.iot.temphumidity.dto.sensor.SensorDataUploadDTO;
import com.iot.temphumidity.dto.sensor.SensorStatisticsDTO;
import com.iot.temphumidity.dto.sensor.SensorStatsDTO;
import com.iot.temphumidity.dto.sensor.DailyStatsDTO;
import com.iot.temphumidity.dto.sensor.HourlyStatsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 传感器标签管理服务接口
 */
public interface SensorTagService {

    // ===== 传感器标签管理 =====
    
    /**
     * 创建传感器标签
     * @param createDTO 传感器标签创建信息
     * @return 创建后的传感器标签信息
     */
    SensorTagDTO createSensorTag(SensorTagCreateDTO createDTO);

    /**
     * 更新传感器标签
     * @param tagId 传感器标签ID
     * @param updateDTO 传感器标签更新信息
     * @return 更新后的传感器标签信息
     */
    SensorTagDTO updateSensorTag(Long tagId, SensorTagUpdateDTO updateDTO);

    /**
     * 获取传感器标签详情
     * @param tagId 传感器标签ID
     * @return 传感器标签详情
     */
    SensorTagDTO getSensorTagById(Long tagId);

    /**
     * 根据设备ID获取传感器标签列表
     * @param deviceId 设备ID
     * @return 传感器标签列表
     */
    List<SensorTagDTO> getSensorTagsByDevice(Long deviceId);

    /**
     * 分页查询传感器标签列表
     * @param pageable 分页参数
     * @return 传感器标签分页列表
     */
    Page<SensorTagDTO> getSensorTags(Pageable pageable);
    SensorDataDTO getLatestSensorData(Long tagId);
    List<SensorTagDTO> getAllSensorTags();

    /**
     * 搜索传感器标签
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 传感器标签分页列表
     */
    Page<SensorTagDTO> searchSensorTags(String keyword, Pageable pageable);

    /**
     * 删除传感器标签
     * @param tagId 传感器标签ID
     */
    void deleteSensorTag(Long tagId);

    /**
     * 批量删除传感器标签
     * @param tagIds 传感器标签ID列表
     */
    void batchDeleteSensorTags(List<Long> tagIds);

    /**
     * 启用/禁用传感器标签
     * @param tagId 传感器标签ID
     * @param enabled 是否启用
     */
    void toggleSensorTag(Long tagId, boolean enabled);

    /**
     * 根据设备ID获取传感器标签列表
     * @param deviceId 设备ID
     * @return 传感器标签列表
     */
    List<SensorTagDTO> getSensorTagsByDeviceId(Long deviceId);

    /**
     * 获取传感器标签统计信息
     * @return 统计信息
     */
    Map<String, Object> getSensorTagStatistics();

    // ===== 传感器数据管理 =====
    
    /**
     * 保存传感器数据
     * @param dataDTO 传感器数据
     * @return 保存后的数据ID
     */
    String saveSensorData(SensorDataDTO dataDTO);

    /**
     * 批量保存传感器数据
     * @param dataList 传感器数据列表
     * @return 保存成功的数量
     */
    int batchSaveSensorData(List<SensorDataDTO> dataList);



    /**
     * 获取传感器历史数据
     * @param queryDTO 查询条件
     * @param pageable 分页参数
     * @return 传感器历史数据分页列表
     */
    Page<SensorDataDTO> getSensorHistoryData(SensorDataQueryDTO queryDTO, Pageable pageable);

    /**
     * 获取传感器统计数据
     * @param tagId 传感器标签ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 传感器统计数据
     */
    SensorStatisticsDTO getSensorStatistics(Long tagId, LocalDateTime startTime, LocalDateTime endTime);

    // ===== SensorDataController 需要的方法 =====
    
    /**
     * 获取传感器数据历史（SensorDataController需要）
     * @param sensorId 传感器ID（字符串）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 分页大小
     * @return 传感器数据历史列表
     */
    List<SensorDataDTO> getSensorDataHistory(String sensorId, LocalDateTime startTime, LocalDateTime endTime, int page, int size);

    /**
     * 获取日统计（SensorDataController需要）
     * @param sensorId 传感器ID（字符串）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日统计数据列表
     */
    List<DailyStatsDTO> getDailySensorStats(String sensorId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取传感器每日统计（SensorDataController和SensorTagController需要）
     * @param tagId 传感器标签ID（Long）
     * @param date 统计日期
     * @return 传感器统计信息
     */
    SensorStatsDTO getDailySensorStats(Long tagId, String date);

    /**
     * 获取小时统计（SensorDataController需要）
     * @param sensorId 传感器ID（字符串）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 小时统计数据列表
     */
    List<HourlyStatsDTO> getHourlySensorStats(String sensorId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取传感器小时统计（SensorDataController和SensorTagController需要）
     * @param tagId 传感器标签ID（Long）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 传感器统计信息列表
     */
    List<SensorStatsDTO> getHourlySensorStats(Long tagId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取设备实时数据（SensorDataController需要）
     * @param deviceId 设备ID（字符串）
     * @return 设备实时数据
     */
    Map<String, Object> getDeviceRealtimeData(String deviceId);

    /**
     * 获取设备数据历史（SensorDataController需要）
     * @param deviceId 设备ID（字符串）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备数据历史
     */
    List<SensorDataDTO> getDeviceDataHistory(String deviceId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取传感器实时数据（从缓存）
     * @param tagId 传感器标签ID
     * @return 实时传感器数据
     */
    SensorDataDTO getSensorRealTimeData(Long tagId);

    /**
     * 获取设备下所有传感器的实时数据
     * @param deviceId 设备ID
     * @return 传感器实时数据列表
     */
    List<SensorDataDTO> getDeviceSensorRealTimeData(Long deviceId);

    /**
     * 获取传感器数据趋势
     * @param tagId 传感器标签ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param interval 时间间隔（分钟）
     * @return 数据趋势列表
     */
    List<Map<String, Object>> getSensorDataTrend(Long tagId, LocalDateTime startTime, LocalDateTime endTime, int interval);

    // ===== 传感器状态管理 =====
    
    /**
     * 更新传感器状态
     * @param tagId 传感器标签ID
     * @param status 状态
     * @param lastSeenTime 最后上报时间
     */
    void updateSensorStatus(Long tagId, String status, LocalDateTime lastSeenTime);

    /**
     * 获取传感器状态
     * @param tagId 传感器标签ID
     * @return 传感器状态
     */
    Map<String, Object> getSensorStatus(Long tagId);

    /**
     * 检查传感器在线状态
     * @param tagId 传感器标签ID
     * @return 是否在线
     */
    boolean checkSensorOnline(Long tagId);

    /**
     * 获取所有离线传感器
     * @return 离线传感器列表
     */
    List<SensorTagDTO> getOfflineSensors();

    /**
     * 获取传感器心跳数据
     * @param tagId 传感器标签ID
     * @return 心跳数据
     */
    Map<String, Object> getSensorHeartbeat(Long tagId);

    // ===== MQTT数据处理 =====
    
    /**
     * 处理MQTT传感器数据
     * @param mqttData MQTT数据
     * @return 处理结果
     */
    Map<String, Object> processMqttSensorData(String mqttData);

    /**
     * 处理MQTT传感器状态更新
     * @param mqttData MQTT数据
     */
    void processMqttSensorStatus(String mqttData);

    /**
     * 处理MQTT传感器配置更新
     * @param mqttData MQTT数据
     */
    void processMqttSensorConfig(String mqttData);

    // ===== 数据上传接口 =====
    
    /**
     * 上传单个传感器数据
     * @param uploadDTO 传感器数据上传DTO
     * @return 上传结果
     */
    SensorDataDTO uploadSingleSensorData(SensorDataUploadDTO uploadDTO);

    /**
     * 批量上传传感器数据
     * @param tagId 传感器标签ID
     * @param uploadDTOs 传感器数据上传DTO列表
     * @return 上传结果列表
     */
    List<SensorDataDTO> uploadBatchSensorData(Long tagId, List<SensorDataUploadDTO> uploadDTOs);

    // ===== 传感器配置管理 =====
    
    /**
     * 获取传感器配置
     * @param tagId 传感器标签ID
     * @return 传感器配置
     */
    Map<String, Object> getSensorConfig(Long tagId);

    /**
     * 更新传感器配置
     * @param tagId 传感器标签ID
     * @param config 配置信息
     */
    void updateSensorConfig(Long tagId, Map<String, Object> config);

    /**
     * 推送传感器配置到设备
     * @param tagId 传感器标签ID
     */
    void pushSensorConfigToDevice(Long tagId);

    /**
     * 获取传感器校准数据
     * @param tagId 传感器标签ID
     * @return 校准数据
     */
    Map<String, Object> getSensorCalibration(Long tagId);

    /**
     * 更新传感器校准数据
     * @param tagId 传感器标签ID
     * @param calibration 校准数据
     */
    void updateSensorCalibration(Long tagId, Map<String, Object> calibration);

    // ===== 数据导出和清理 =====
    
    /**
     * 导出传感器数据
     * @param queryDTO 查询条件
     * @return CSV格式数据
     */
    String exportSensorData(SensorDataQueryDTO queryDTO);

    /**
     * 清理过期传感器数据
     * @param days 保留天数
     * @return 清理的数据量
     */
    int cleanupExpiredSensorData(int days);
}