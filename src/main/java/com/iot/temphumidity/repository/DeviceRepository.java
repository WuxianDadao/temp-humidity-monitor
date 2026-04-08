package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    
    /**
     * 根据设备标识查找设备
     */
    Optional<Device> findByDeviceIdentifier(String deviceIdentifier);
    
    /**
     * 根据设备类型查找设备列表
     */
    List<Device> findByDeviceType(String deviceType);
    
    /**
     * 根据设备状态查找设备列表
     */
    List<Device> findByDeviceStatus(String deviceStatus);
    
    /**
     * 根据用户ID查找用户绑定的设备
     */
    @Query("SELECT d FROM Device d JOIN d.users u WHERE u.id = :userId")
    List<Device> findByUserId(@Param("userId") Long userId);
    
    /**
     * 根据设备标识检查设备是否存在
     */
    boolean existsByDeviceIdentifier(String deviceIdentifier);
    
    /**
     * 根据设备名称查找设备
     */
    List<Device> findByDeviceNameContainingIgnoreCase(String deviceName);
    
    /**
     * 根据位置查找设备
     */
    List<Device> findByLocationContainingIgnoreCase(String location);
    
    /**
     * 根据厂商查找设备
     */
    List<Device> findByManufacturer(String manufacturer);
    
    /**
     * 根据固件版本查找设备
     */
    List<Device> findByFirmwareVersion(String firmwareVersion);
    
    /**
     * 查找在线设备
     */
    @Query("SELECT d FROM Device d WHERE d.deviceStatus = 'ONLINE'")
    List<Device> findOnlineDevices();
    
    /**
     * 查找离线设备
     */
    @Query("SELECT d FROM Device d WHERE d.deviceStatus = 'OFFLINE'")
    List<Device> findOfflineDevices();
    
    /**
     * 查找指定时间阈值前的离线设备
     */
    @Query("SELECT d FROM Device d WHERE d.deviceStatus = 'OFFLINE' AND d.lastHeartbeatTime <= :threshold")
    List<Device> findOfflineDevices(@Param("threshold") LocalDateTime threshold);
    
    /**
     * 根据设备状态统计设备数量
     */
    @Query("SELECT d.deviceStatus, COUNT(d) FROM Device d GROUP BY d.deviceStatus")
    List<Object[]> countDevicesByStatus();
    
    /**
     * 根据设备类型统计设备数量
     */
    @Query("SELECT d.deviceType, COUNT(d) FROM Device d GROUP BY d.deviceType")
    List<Object[]> countDevicesByType();
    
    /**
     * 根据ICCID检查设备是否存在
     */
    boolean existsByIccid(String iccid);
    
    /**
     * 根据序列号检查设备是否存在
     */
    boolean existsBySerialNumber(String serialNumber);
}