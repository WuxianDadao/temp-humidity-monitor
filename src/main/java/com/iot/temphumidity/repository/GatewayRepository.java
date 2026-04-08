package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, Long> {
    
    /**
     * 根据网关标识查找网关
     */
    Optional<Gateway> findByGatewayIdentifier(String gatewayIdentifier);
    
    /**
     * 根据网关状态查找网关列表
     */
    List<Gateway> findByStatus(String status);
    
    /**
     * 根据网关类型查找网关列表
     */
    List<Gateway> findByGatewayType(String gatewayType);
    
    /**
     * 根据用户ID查找用户绑定的网关
     */
    @Query("SELECT g FROM Gateway g JOIN g.users u WHERE u.id = :userId")
    List<Gateway> findByUserId(@Param("userId") Long userId);
    
    /**
     * 根据网关标识检查网关是否存在
     */
    boolean existsByGatewayIdentifier(String gatewayIdentifier);
    
    /**
     * 根据网关名称查找网关
     */
    List<Gateway> findByGatewayNameContainingIgnoreCase(String gatewayName);
    
    /**
     * 根据网关标识或名称搜索网关
     */
    @Query("SELECT g FROM Gateway g WHERE LOWER(g.gatewayIdentifier) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(g.gatewayName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Gateway> searchByIdentifierOrName(@Param("keyword") String keyword);
    
    /**
     * 根据位置查找网关
     */
    List<Gateway> findByLocationContainingIgnoreCase(String location);
    
    /**
     * 根据厂商查找网关
     */
    List<Gateway> findByManufacturer(String manufacturer);
    
    /**
     * 根据固件版本查找网关
     */
    List<Gateway> findByFirmwareVersion(String firmwareVersion);
    
    /**
     * 根据SIM卡ICCID查找网关
     */
    Optional<Gateway> findBySimCardIccid(String simCardIccid);
    
    /**
     * 根据网络运营商查找网关
     */
    List<Gateway> findByNetworkOperator(String networkOperator);
    
    /**
     * 查找在线网关
     */
    @Query("SELECT g FROM Gateway g WHERE g.status = 'ONLINE'")
    List<Gateway> findOnlineGateways();
    
    /**
     * 查找离线网关
     */
    @Query("SELECT g FROM Gateway g WHERE g.status = 'OFFLINE'")
    List<Gateway> findOfflineGateways();
    
    /**
     * 根据网关状态统计网关数量
     */
    @Query("SELECT COUNT(g) FROM Gateway g WHERE g.status = :status")
    long countByStatus(@Param("status") String status);
    
    /**
     * 根据网关状态分组统计网关数量
     */
    @Query("SELECT g.status, COUNT(g) FROM Gateway g GROUP BY g.status")
    List<Object[]> countGatewaysByStatus();
    
    /**
     * 根据网关类型统计网关数量
     */
    @Query("SELECT g.gatewayType, COUNT(g) FROM Gateway g GROUP BY g.gatewayType")
    List<Object[]> countGatewaysByType();
    
    /**
     * 根据网络运营商统计网关数量
     */
    @Query("SELECT g.networkOperator, COUNT(g) FROM Gateway g GROUP BY g.networkOperator")
    List<Object[]> countGatewaysByNetworkOperator();
    
    /**
     * 查找最近更新的网关
     */
    @Query("SELECT g FROM Gateway g ORDER BY g.lastHeartbeatTime DESC NULLS LAST")
    List<Gateway> findRecentlyUpdatedGateways();
    
    /**
     * 查找需要维护的网关
     */
    @Query("SELECT g FROM Gateway g WHERE g.status = 'MAINTENANCE' OR g.status = 'FAULTY'")
    List<Gateway> findGatewaysNeedingMaintenance();
}