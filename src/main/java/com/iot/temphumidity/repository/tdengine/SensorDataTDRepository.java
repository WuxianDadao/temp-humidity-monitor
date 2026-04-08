package com.iot.temphumidity.repository.tdengine;

import com.iot.temphumidity.entity.tdengine.SensorDataTD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TDengine传感器数据仓库接口
 * 用于操作TDengine中的传感器时序数据
 */
@Repository
public interface SensorDataTDRepository {
    
    /**
     * 保存传感器数据
     */
    SensorDataTD save(SensorDataTD sensorData);
    
    /**
     * 批量保存传感器数据
     */
    List<SensorDataTD> saveAll(Iterable<SensorDataTD> sensorDataList);
    
    /**
     * 根据ID查找传感器数据
     */
    Optional<SensorDataTD> findById(Long id);
    
    /**
     * 根据传感器ID查找最新的数据
     */
    Optional<SensorDataTD> findLatestBySensorId(Long sensorId);
    
    /**
     * 根据时间段查询传感器数据
     */
    List<SensorDataTD> findBySensorIdAndTimeRange(Long sensorId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据时间段分页查询传感器数据
     */
    Page<SensorDataTD> findBySensorIdAndTimeRange(Long sensorId, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    
    /**
     * 查询多个传感器的数据
     */
    List<SensorDataTD> findBySensorIdsAndTimeRange(List<Long> sensorIds, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查询网关下的所有传感器数据
     */
    List<SensorDataTD> findByGatewayIdAndTimeRange(Long gatewayId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 删除指定时间前的历史数据
     */
    long deleteByTimestampBefore(LocalDateTime timestamp);
    
    /**
     * 统计传感器数据数量
     */
    long countBySensorId(Long sensorId);
    
    /**
     * 统计时间段内的数据数量
     */
    long countBySensorIdAndTimeRange(Long sensorId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 检查传感器是否存在数据
     */
    boolean existsBySensorId(Long sensorId);
    
    /**
     * 获取传感器数据的时间范围
     */
    List<LocalDateTime> getTimeRangeBySensorId(Long sensorId);
}