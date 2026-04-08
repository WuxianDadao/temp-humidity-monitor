package com.iot.temphumidity.service;

import com.iot.temphumidity.entity.tdengine.SensorDataTD;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * TDengine传感器数据服务
 * 负责传感器时序数据的存储和查询
 */
@Service
public interface TDengineSensorDataService {
    
    /**
     * 插入单条传感器数据
     */
    boolean insertSensorData(SensorDataTD sensorData);
    
    /**
     * 批量插入传感器数据
     */
    int batchInsertSensorData(List<SensorDataTD> sensorDataList);
    
    /**
     * 查询指定设备的最近N条数据
     */
    List<SensorDataTD> queryRecentData(String deviceId, int limit);
    
    /**
     * 查询指定时间段内的数据
     */
    List<SensorDataTD> queryDataByTimeRange(String deviceId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询指定设备的统计信息
     */
    Map<String, Object> getDeviceStatistics(String deviceId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询多个设备的实时数据
     */
    List<SensorDataTD> queryRealTimeData(List<String> deviceIds);
    
    /**
     * 查询报警相关的历史数据
     */
    List<SensorDataTD> queryAlarmHistoryData(String deviceId, LocalDateTime alarmTime, int hoursBefore);
    
    /**
     * 检查设备连接状态
     */
    boolean checkDeviceConnection(String deviceId);
    
    /**
     * 清理过期数据
     */
    int cleanupOldData(int daysToKeep);
}