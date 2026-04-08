package com.iot.temphumidity.repository.postgresql;

import com.iot.temphumidity.entity.postgresql.SensorTagEntity;
import com.iot.temphumidity.enums.SensorStatus;
import com.iot.temphumidity.enums.SensorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 传感器标签数据访问接口
 */
@Repository
public interface SensorTagRepository extends JpaRepository<SensorTagEntity, Long> {
    
    Optional<SensorTagEntity> findByTagId(String tagId);
    
    List<SensorTagEntity> findByGatewayId(Long gatewayId);
    
    List<SensorTagEntity> findByDeviceId(Long deviceId);
    
    List<SensorTagEntity> findByGatewayIdAndStatus(Long gatewayId, SensorStatus status);
    
    Page<SensorTagEntity> findByGatewayId(Long gatewayId, Pageable pageable);
    
    List<SensorTagEntity> findBySensorType(SensorType sensorType);
    
    @Query("SELECT s FROM SensorTagEntity s WHERE s.gatewayId = :gatewayId AND s.name LIKE %:keyword%")
    List<SensorTagEntity> searchByGatewayAndKeyword(@Param("gatewayId") Long gatewayId, 
                                                   @Param("keyword") String keyword);
    
    // TODO: Fix - SensorTagEntity doesn't have userId field
    // @Query("SELECT s FROM SensorTagEntity s WHERE s.userId = :userId AND s.status = 'ACTIVE'")
    // List<SensorTagEntity> findActiveSensorsByUser(@Param("userId") Long userId);
    
    long countByGatewayId(Long gatewayId);
    
    long countByGatewayIdAndStatus(Long gatewayId, SensorStatus status);
    
    @Query("SELECT COUNT(s) FROM SensorTagEntity s WHERE s.gatewayId = :gatewayId AND s.sensorType = :sensorType")
    long countByGatewayIdAndSensorType(@Param("gatewayId") Long gatewayId, 
                                       @Param("sensorType") SensorType sensorType);
    
    @Query("SELECT s FROM SensorTagEntity s WHERE s.lastDataReportTime < :timeoutTime AND s.status = 'ACTIVE'")
    List<SensorTagEntity> findTimeoutSensors(@Param("timeoutTime") LocalDateTime timeoutTime);
    
    @Query("SELECT new map(s.gatewayId as gatewayId, COUNT(s) as totalSensors, " +
           "SUM(CASE WHEN s.status = 'ACTIVE' THEN 1 ELSE 0 END) as activeSensors, " +
           "SUM(CASE WHEN s.status = 'ABNORMAL' THEN 1 ELSE 0 END) as abnormalSensors, " +
           "SUM(CASE WHEN s.batteryLevel < 2.0 THEN 1 ELSE 0 END) as lowBatterySensors) " +
           "FROM SensorTagEntity s " +
           "WHERE s.gatewayId IN :gatewayIds " +
           "GROUP BY s.gatewayId")
    List<Object[]> findSensorStatisticsByGateways(@Param("gatewayIds") List<Long> gatewayIds);
    
    @Query("SELECT s FROM SensorTagEntity s WHERE s.batteryLevel < :batteryThreshold")
    List<SensorTagEntity> findLowBatterySensors(@Param("batteryThreshold") Double batteryThreshold);
    
    @Query("SELECT s FROM SensorTagEntity s WHERE s.rssi < :rssiThreshold")
    List<SensorTagEntity> findWeakSignalSensors(@Param("rssiThreshold") Integer rssiThreshold);
    
    @Query(value = "SELECT * FROM iot.sensor_tag WHERE ST_Distance_Sphere(" +
                   "point(longitude, latitude), point(:longitude, :latitude)) < :distanceMeters", 
           nativeQuery = true)
    List<SensorTagEntity> findNearbySensors(@Param("longitude") Double longitude,
                                           @Param("latitude") Double latitude,
                                           @Param("distanceMeters") Integer distanceMeters);
}