package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.SensorTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorTagRepository extends JpaRepository<SensorTag, Long> {
    
    /**
     * 根据传感器标签标识查找传感器
     */
    Optional<SensorTag> findByTagIdentifier(String tagIdentifier);
    
    /**
     * 根据传感器类型查找传感器列表
     */
    List<SensorTag> findBySensorType(String sensorType);
    
    /**
     * 根据传感器状态查找传感器列表
     */
    List<SensorTag> findByStatus(String status);
    
    /**
     * 根据网关ID查找网关下的所有传感器
     */
    @Query("SELECT s FROM SensorTag s WHERE s.gateway.id = :gatewayId")
    List<SensorTag> findByGatewayId(@Param("gatewayId") Long gatewayId);
    
    /**
     * 根据用户ID查找用户可访问的传感器
     */
    @Query("SELECT s FROM SensorTag s JOIN s.gateway g JOIN g.users u WHERE u.id = :userId")
    List<SensorTag> findByUserId(@Param("userId") Long userId);
    
    /**
     * 根据传感器标识检查传感器是否存在
     */
    boolean existsByTagIdentifier(String tagIdentifier);
    
    /**
     * 根据传感器名称查找传感器
     */
    List<SensorTag> findByTagNameContainingIgnoreCase(String tagName);
    
    /**
     * 根据位置查找传感器
     */
    List<SensorTag> findByLocationContainingIgnoreCase(String location);
    
    /**
     * 查找在线的传感器
     */
    @Query("SELECT s FROM SensorTag s WHERE s.status = 'ONLINE'")
    List<SensorTag> findOnlineSensorTags();
    
    /**
     * 查找离线的传感器
     */
    @Query("SELECT s FROM SensorTag s WHERE s.status = 'OFFLINE'")
    List<SensorTag> findOfflineSensorTags();
    
    /**
     * 根据传感器类型统计传感器数量
     */
    @Query("SELECT s.sensorType, COUNT(s) FROM SensorTag s GROUP BY s.sensorType")
    List<Object[]> countSensorTagsByType();
    
    /**
     * 根据传感器状态统计传感器数量
     */
    @Query("SELECT s.status, COUNT(s) FROM SensorTag s GROUP BY s.status")
    List<Object[]> countSensorTagsByStatus();
    
    /**
     * 根据网关统计传感器数量
     */
    @Query("SELECT s.gateway.id, s.gateway.gatewayName, COUNT(s) FROM SensorTag s GROUP BY s.gateway.id, s.gateway.gatewayName")
    List<Object[]> countSensorTagsByGateway();
    
    /**
     * 查找需要维护的传感器
     */
    @Query("SELECT s FROM SensorTag s WHERE s.status = 'MAINTENANCE' OR s.status = 'FAULTY'")
    List<SensorTag> findSensorTagsNeedingMaintenance();
    
    /**
     * 根据电池电量查找低电量传感器
     */
    @Query("SELECT s FROM SensorTag s WHERE s.batteryLevel < 20")
    List<SensorTag> findLowBatterySensorTags();
    
    /**
     * 根据信号强度查找弱信号传感器
     */
    @Query("SELECT s FROM SensorTag s WHERE s.rssi < -80")
    List<SensorTag> findWeakSignalSensorTags();
    
    /**
     * 查找最近更新的传感器
     */
    @Query("SELECT s FROM SensorTag s ORDER BY s.lastReportTime DESC NULLS LAST")
    List<SensorTag> findRecentlyUpdatedSensorTags();
}