package com.iot.temphumidity.repository.postgresql;

import com.iot.temphumidity.entity.postgresql.GatewayEntity;
import com.iot.temphumidity.enums.GatewayStatus;
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
 * 网关数据访问接口
 */
@Repository
public interface GatewayRepository extends JpaRepository<GatewayEntity, Long> {
    
    Optional<GatewayEntity> findByIccid(String iccid);
    
    List<GatewayEntity> findByUserId(Long userId);
    
    Page<GatewayEntity> findByUserId(Long userId, Pageable pageable);
    
    List<GatewayEntity> findByStatus(GatewayStatus status);
    
    long countByUserId(Long userId);
    
    long countByStatus(GatewayStatus status);
    
    @Query("SELECT g FROM GatewayEntity g WHERE g.lastOnlineTime < :timeoutTime AND g.status = :onlineStatus")
    List<GatewayEntity> findTimeouts(@Param("timeoutTime") LocalDateTime timeoutTime, 
                                     @Param("onlineStatus") GatewayStatus onlineStatus);
    
    @Query("SELECT g FROM GatewayEntity g WHERE g.userId = :userId AND g.name LIKE %:keyword%")
    List<GatewayEntity> searchByUserAndKeyword(@Param("userId") Long userId, 
                                              @Param("keyword") String keyword);
    
    boolean existsByIccid(String iccid);
    
    @Query("SELECT COUNT(g) FROM GatewayEntity g WHERE g.userId = :userId AND g.status = :status")
    long countByUserIdAndStatus(@Param("userId") Long userId, 
                               @Param("status") GatewayStatus status);
    
    @Query(value = "SELECT * FROM iot.gateway WHERE ST_Distance_Sphere(" +
                   "point(longitude, latitude), point(:longitude, :latitude)) < :distanceMeters", 
           nativeQuery = true)
    List<GatewayEntity> findNearbyGateways(@Param("longitude") Double longitude,
                                          @Param("latitude") Double latitude,
                                          @Param("distanceMeters") Integer distanceMeters);
    
    @Query("SELECT new map(g.id as id, g.name as name, g.iccid as iccid, g.status as status, " +
           "COUNT(s) as sensorCount, " +
           "SUM(CASE WHEN s.status = 'ACTIVE' THEN 1 ELSE 0 END) as activeSensorCount) " +
           "FROM GatewayEntity g LEFT JOIN g.sensorTags s " +
           "WHERE g.userId = :userId " +
           "GROUP BY g.id, g.name, g.iccid, g.status")
    List<Object[]> findGatewaySummaryByUser(@Param("userId") Long userId);
}