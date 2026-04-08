package com.iot.temphumidity.service;

import com.iot.temphumidity.dto.device.DeviceCreateDTO;
import com.iot.temphumidity.dto.device.DeviceDTO;
import com.iot.temphumidity.dto.device.DeviceUpdateDTO;
import com.iot.temphumidity.dto.device.DeviceStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 设备管理服务接口
 */
public interface DeviceService {

    /**
     * 创建设备
     * @param deviceCreateDTO 设备创建信息
     * @return 创建后的设备信息
     */
    DeviceDTO createDevice(DeviceCreateDTO deviceCreateDTO);

    /**
     * 更新设备信息
     * @param deviceId 设备ID
     * @param deviceUpdateDTO 设备更新信息
     * @return 更新后的设备信息
     */
    DeviceDTO updateDevice(Long deviceId, DeviceUpdateDTO deviceUpdateDTO);

    /**
     * 获取设备详情
     * @param deviceId 设备ID
     * @return 设备详情
     */
    DeviceDTO getDeviceById(Long deviceId);

    /**
     * 根据ICCID获取设备
     * @param iccid 设备ICCID
     * @return 设备信息
     */
    DeviceDTO getDeviceByIccid(String iccid);

    /**
     * 分页查询设备列表
     * @param pageable 分页参数
     * @return 设备分页列表
     */
    Page<DeviceDTO> getDevices(Pageable pageable);

    /**
     * 根据组织ID分页查询设备
     * @param organizationId 组织ID
     * @param pageable 分页参数
     * @return 设备分页列表
     */
    Page<DeviceDTO> getDevicesByOrganization(Long organizationId, Pageable pageable);

    /**
     * 根据用户ID分页查询设备
     * @param userId 用户ID
     * @param pageable 分页参数
     * @return 设备分页列表
     */
    Page<DeviceDTO> getDevicesByUser(Long userId, Pageable pageable);

    /**
     * 删除设备
     * @param deviceId 设备ID
     */
    void deleteDevice(Long deviceId);

    /**
     * 启用/禁用设备
     * @param deviceId 设备ID
     * @param enabled 是否启用
     */
    void toggleDeviceStatus(Long deviceId, boolean enabled);

    /**
     * 更新设备在线状态
     * @param iccid 设备ICCID
     * @param online 是否在线
     * @param lastSeen 最后在线时间
     */
    void updateDeviceOnlineStatus(String iccid, boolean online, String lastSeen);

    /**
     * 获取设备状态
     * @param deviceId 设备ID
     * @return 设备状态信息
     */
    DeviceStatusDTO getDeviceStatus(Long deviceId);

    /**
     * 绑定传感器标签到设备
     * @param deviceId 设备ID
     * @param tagIds 传感器标签ID列表
     */
    void bindTagsToDevice(Long deviceId, List<Long> tagIds);

    /**
     * 解绑传感器标签
     * @param deviceId 设备ID
     * @param tagId 传感器标签ID
     */
    void unbindTagFromDevice(Long deviceId, Long tagId);

    /**
     * 获取设备的传感器标签列表
     * @param deviceId 设备ID
     * @return 传感器标签ID列表
     */
    List<Long> getDeviceTags(Long deviceId);

    /**
     * 获取离线设备列表
     * @param minutes 离线分钟数
     * @return 离线设备列表
     */
    List<DeviceDTO> getOfflineDevices(int minutes);
}