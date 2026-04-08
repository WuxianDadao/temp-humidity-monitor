package com.iot.temphumidity.service;

import com.iot.temphumidity.dto.device.DeviceRegisterDTO;
import com.iot.temphumidity.dto.device.DeviceStatusDTO;
import com.iot.temphumidity.entity.Gateway;
import com.iot.temphumidity.enums.GatewayStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 网关管理服务接口
 */
public interface GatewayService {
    
    /**
     * 注册新网关
     */
    Gateway registerGateway(DeviceRegisterDTO registerDTO);
    
    /**
     * 根据ID获取网关
     */
    Gateway getGatewayById(Long gatewayId);
    
    /**
     * 根据标识获取网关
     */
    Gateway getGatewayByIdentifier(String gatewayIdentifier);
    
    /**
     * 根据ICCID获取网关
     */
    Gateway getGatewayByIccid(String iccid);
    
    /**
     * 获取所有网关
     */
    List<Gateway> getAllGateways();
    
    /**
     * 分页查询网关
     */
    Page<Gateway> getGatewaysByPage(Pageable pageable);
    
    /**
     * 更新网关信息
     */
    Gateway updateGateway(Long gatewayId, Map<String, Object> updates);
    
    /**
     * 删除网关
     */
    void deleteGateway(Long gatewayId);
    
    /**
     * 绑定网关到用户
     */
    void bindGatewayToUser(Long gatewayId, Long userId);
    
    /**
     * 解绑网关用户
     */
    void unbindGatewayFromUser(Long gatewayId, Long userId);
    
    /**
     * 更新网关状态
     */
    void updateGatewayStatus(String gatewayIdentifier, GatewayStatus status);
    
    /**
     * 更新网关心跳
     */
    void updateGatewayHeartbeat(String gatewayIdentifier);
    
    /**
     * 获取网关状态信息
     */
    DeviceStatusDTO getGatewayStatus(String gatewayIdentifier);
    
    /**
     * 获取在线网关列表
     */
    List<Gateway> getOnlineGateways();
    
    /**
     * 获取离线网关列表
     */
    List<Gateway> getOfflineGateways();
    
    /**
     * 获取网关统计信息
     */
    Map<String, Object> getGatewayStatistics();
    
    /**
     * 根据用户ID获取网关列表
     */
    List<Gateway> getGatewaysByUserId(Long userId);
    
    /**
     * 搜索网关
     */
    List<Gateway> searchGateways(String keyword);
    
    /**
     * 检查网关是否存在
     */
    boolean existsGatewayByIdentifier(String gatewayIdentifier);
    
    /**
     * 获取网关数量统计
     */
    long countGateways();
    
    /**
     * 更新网关最后心跳时间
     */
    void updateGatewayLastHeartbeat(String gatewayIdentifier);
}