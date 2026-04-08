package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.DeviceTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备标签关联Repository接口
 */
@Repository
public interface DeviceTagRepository extends JpaRepository<DeviceTag, Long> {
    
    List<DeviceTag> findByDeviceId(Long deviceId);
    
    List<DeviceTag> findBySensorTagId(Long sensorTagId);
    
    boolean existsByDeviceIdAndSensorTagId(Long deviceId, Long sensorTagId);
    
    // 别名方法，兼容旧代码
    default boolean existsByDeviceIdAndTagId(Long deviceId, Long tagId) {
        return existsByDeviceIdAndSensorTagId(deviceId, tagId);
    }
    
    void deleteByDeviceIdAndSensorTagId(Long deviceId, Long sensorTagId);
    
    @Query("SELECT dt.sensorTagId FROM DeviceTag dt WHERE dt.deviceId = :deviceId")
    List<Long> findSensorTagIdsByDeviceId(@Param("deviceId") Long deviceId);
    
    @Query("SELECT dt.deviceId FROM DeviceTag dt WHERE dt.sensorTagId = :sensorTagId")
    List<Long> findDeviceIdsBySensorTagId(@Param("sensorTagId") Long sensorTagId);
    
    @Query("SELECT COUNT(dt) FROM DeviceTag dt WHERE dt.deviceId = :deviceId")
    Long countSensorTagsByDevice(@Param("deviceId") Long deviceId);
    
    @Query("SELECT dt.deviceId, COUNT(dt) FROM DeviceTag dt GROUP BY dt.deviceId")
    List<Object[]> countSensorTagsPerDevice();
}