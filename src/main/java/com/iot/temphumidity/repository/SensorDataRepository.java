package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 传感器数据仓库接口
 */
@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    
    /**
     * 根据传感器ID查找数据
     */
    List<SensorData> findBySensorId(String sensorId);
    
    /**
     * 根据设备ID查找数据
     */
    List<SensorData> findByDeviceId(String deviceId);
    
    /**
     * 根据网关ID查找数据
     */
    List<SensorData> findByGatewayId(String gatewayId);
    
    /**
     * 根据传感器ID和时间范围查找数据
     */
    List<SensorData> findBySensorIdAndSampledAtBetween(String sensorId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据设备ID和时间范围查找数据
     */
    List<SensorData> findByDeviceIdAndSampledAtBetween(String deviceId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 查找未处理的数据
     */
    List<SensorData> findByProcessedAtIsNull();
    
    /**
     * 查找未存储在TDengine中的数据
     */
    List<SensorData> findByStoredInTdengineFalse();
    
    /**
     * 查找需要报警检查的数据
     */
    List<SensorData> findByAlertCheckedFalseAndProcessedAtIsNotNull();
    
    /**
     * 根据传感器ID查找最新的数据
     */
    Optional<SensorData> findFirstBySensorIdOrderBySampledAtDesc(String sensorId);
    
    /**
     * 根据设备ID查找最新的数据
     */
    Optional<SensorData> findFirstByDeviceIdOrderBySampledAtDesc(String deviceId);
    
    /**
     * 统计传感器数据数量
     */
    long countBySensorId(String sensorId);
    
    /**
     * 统计设备数据数量
     */
    long countByDeviceId(String deviceId);
    
    /**
     * 统计网关数据数量
     */
    long countByGatewayId(String gatewayId);
    
    /**
     * 统计时间范围内的数据数量
     */
    long countBySampledAtBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 删除过期的传感器数据
     */
    void deleteBySampledAtBefore(LocalDateTime expirationTime);
    
    /**
     * 获取传感器的最新数据质量
     */
    @Query("SELECT s.dataQuality FROM SensorData s WHERE s.sensorId = :sensorId ORDER BY s.sampledAt DESC LIMIT 1")
    Optional<String> findLatestDataQualityBySensorId(@Param("sensorId") String sensorId);
    
    /**
     * 获取设备的最新电池电压
     */
    @Query("SELECT s.batteryVoltage FROM SensorData s WHERE s.deviceId = :deviceId AND s.batteryVoltage IS NOT NULL ORDER BY s.sampledAt DESC LIMIT 1")
    Optional<Double> findLatestBatteryVoltageByDeviceId(@Param("deviceId") String deviceId);
    
    /**
     * 获取传感器数据统计信息
     */
    @Query("SELECT COUNT(s), AVG(s.temperature), AVG(s.humidity) FROM SensorData s WHERE s.sensorId = :sensorId AND s.sampledAt BETWEEN :startTime AND :endTime")
    Object[] getSensorStats(@Param("sensorId") String sensorId, 
                           @Param("startTime") LocalDateTime startTime, 
                           @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查找有报警的数据
     */
    List<SensorData> findByAlertTriggeredTrue();
    
    /**
     * 根据报警ID查找数据
     */
    List<SensorData> findByAlertId(String alertId);
}