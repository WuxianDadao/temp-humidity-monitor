package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.SensorConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 传感器配置Repository
 */
@Repository
public interface SensorConfigRepository extends JpaRepository<SensorConfig, Long> {
    
    /**
     * 根据传感器标签ID查找配置
     */
    Optional<SensorConfig> findBySensorTagId(Long sensorTagId);
    
    /**
     * 根据设备ID查找配置列表
     */
    List<SensorConfig> findByDeviceId(Long deviceId);
    
    /**
     * 根据网关ID查找配置列表
     */
    List<SensorConfig> findByGatewayId(Long gatewayId);
    
    /**
     * 查找需要同步配置的设备
     */
    @Query("SELECT sc FROM SensorConfig sc WHERE " +
           "sc.lastConfigSyncTime IS NULL OR " +
           "sc.lastConfigSyncTime < :syncBeforeTime " +
           "AND sc.configStatus = 'ACTIVE'")
    List<SensorConfig> findConfigsNeedingSync(@Param("syncBeforeTime") LocalDateTime syncBeforeTime);
    
    /**
     * 查找超过指定时间未收到数据的传感器配置
     */
    @Query("SELECT sc FROM SensorConfig sc WHERE " +
           "sc.lastDataReceiveTime IS NULL OR " +
           "sc.lastDataReceiveTime < :receiveBeforeTime " +
           "AND sc.configStatus = 'ACTIVE'")
    List<SensorConfig> findConfigsWithNoRecentData(@Param("receiveBeforeTime") LocalDateTime receiveBeforeTime);
    
    /**
     * 根据配置状态查找
     */
    List<SensorConfig> findByConfigStatus(String configStatus);
    
    /**
     * 根据传感器标签ID列表查找配置
     */
    List<SensorConfig> findBySensorTagIdIn(List<Long> sensorTagIds);
}