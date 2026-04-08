package com.iot.temphumidity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iot.temphumidity.entity.DeviceGateway;

import java.util.List;

/**
 * 设备网关仓库接口
 */
@Repository
public interface DeviceGatewayRepository extends JpaRepository<DeviceGateway, Long> {
    
    /**
     * 根据设备ID查找传感器ID列表
     * @param deviceId 设备ID
     * @return 传感器ID列表
     */
    @Query("SELECT dg.sensorId FROM DeviceGateway dg WHERE dg.deviceId = :deviceId")
    List<String> findSensorIdsByDeviceId(@Param("deviceId") String deviceId);
    
    /**
     * 根据设备ID查找网关
     * @param deviceId 设备ID
     * @return 网关信息
     */
    DeviceGateway findByDeviceId(String deviceId);
    
    /**
     * 检查设备是否存在
     * @param deviceId 设备ID
     * @return 是否存在
     */
    boolean existsByDeviceId(String deviceId);
    
    /**
     * 根据传感器ID查找设备ID
     * @param sensorId 传感器ID
     * @return 设备ID
     */
    @Query("SELECT dg.deviceId FROM DeviceGateway dg WHERE dg.sensorId = :sensorId")
    String findDeviceIdBySensorId(@Param("sensorId") String sensorId);
    
    /**
     * 获取在线设备列表
     * @return 在线设备ID列表
     */
    @Query("SELECT dg.deviceId FROM DeviceGateway dg WHERE dg.isOnline = true")
    List<String> findOnlineDeviceIds();
    
    /**
     * 更新设备在线状态
     * @param deviceId 设备ID
     * @param isOnline 是否在线
     * @return 更新的记录数
     */
    @Query("UPDATE DeviceGateway dg SET dg.isOnline = :isOnline, dg.lastHeartbeat = CURRENT_TIMESTAMP WHERE dg.deviceId = :deviceId")
    int updateDeviceOnlineStatus(@Param("deviceId") String deviceId, @Param("isOnline") boolean isOnline);
}