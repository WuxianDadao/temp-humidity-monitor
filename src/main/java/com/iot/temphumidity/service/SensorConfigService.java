package com.iot.temphumidity.service;

import com.iot.temphumidity.dto.sensorconfig.SensorConfigCreateDTO;
import com.iot.temphumidity.dto.sensorconfig.SensorConfigDTO;
import com.iot.temphumidity.dto.sensorconfig.SensorConfigUpdateDTO;
import com.iot.temphumidity.dto.sensorconfig.SensorConfigSyncDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 传感器配置服务接口
 */
public interface SensorConfigService {
    
    /**
     * 创建传感器配置
     */
    SensorConfigDTO createSensorConfig(SensorConfigCreateDTO createDTO);
    
    /**
     * 更新传感器配置
     */
    SensorConfigDTO updateSensorConfig(Long configId, SensorConfigUpdateDTO updateDTO);
    
    /**
     * 根据传感器标签ID创建或更新配置
     */
    SensorConfigDTO createOrUpdateConfigBySensorTag(Long sensorTagId, SensorConfigCreateDTO createDTO);
    
    /**
     * 获取传感器配置详情
     */
    SensorConfigDTO getSensorConfigById(Long configId);
    
    /**
     * 根据传感器标签ID获取配置
     */
    SensorConfigDTO getSensorConfigBySensorTagId(Long sensorTagId);
    
    /**
     * 根据设备ID获取配置列表
     */
    List<SensorConfigDTO> getSensorConfigsByDeviceId(Long deviceId);
    
    /**
     * 根据网关ID获取配置列表
     */
    List<SensorConfigDTO> getSensorConfigsByGatewayId(Long gatewayId);
    
    /**
     * 分页查询传感器配置
     */
    Page<SensorConfigDTO> getSensorConfigPage(Pageable pageable);
    
    /**
     * 删除传感器配置
     */
    void deleteSensorConfig(Long configId);
    
    /**
     * 批量同步配置到设备
     */
    List<SensorConfigSyncDTO> syncConfigsToDevices(List<Long> configIds);
    
    /**
     * 检查传感器数据是否触发报警
     */
    boolean checkSensorDataAlarm(Long sensorTagId, Double temperature, Double humidity, 
                                Double batteryLevel, Integer rssi);
    
    /**
     * 更新最后数据接收时间
     */
    void updateLastDataReceiveTime(Long sensorTagId);
    
    /**
     * 获取需要同步配置的设备列表
     */
    List<SensorConfigSyncDTO> getConfigsNeedingSync();
}