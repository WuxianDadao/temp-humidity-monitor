package com.iot.temphumidity.repository.postgresql;

import com.iot.temphumidity.entity.postgresql.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 设备数据访问接口
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    
    Optional<DeviceEntity> findByDeviceSn(String deviceSn);
    
    List<DeviceEntity> findByGatewayId(Long gatewayId);
    
    Page<DeviceEntity> findByGatewayId(Long gatewayId, Pageable pageable);
    
    @Query("SELECT d FROM DeviceEntity d WHERE d.gatewayId = :gatewayId AND d.name LIKE %:keyword%")
    List<DeviceEntity> searchByGatewayAndKeyword(@Param("gatewayId") Long gatewayId, 
                                                @Param("keyword") String keyword);
    
    boolean existsByDeviceSn(String deviceSn);
    
    long countByGatewayId(Long gatewayId);
    
    @Query("SELECT d FROM DeviceEntity d WHERE d.gatewayId IN :gatewayIds")
    List<DeviceEntity> findByGatewayIds(@Param("gatewayIds") List<Long> gatewayIds);
    
    @Query("SELECT new map(d.gatewayId as gatewayId, COUNT(d) as deviceCount, " +
           "COUNT(DISTINCT d.manufacturer) as manufacturerCount) " +
           "FROM DeviceEntity d " +
           "WHERE d.gatewayId IN :gatewayIds " +
           "GROUP BY d.gatewayId")
    List<Object[]> findDeviceStatisticsByGateways(@Param("gatewayIds") List<Long> gatewayIds);
    
    @Query(value = "SELECT * FROM iot.device WHERE ST_Distance_Sphere(" +
                   "point(longitude, latitude), point(:longitude, :latitude)) < :distanceMeters", 
           nativeQuery = true)
    List<DeviceEntity> findNearbyDevices(@Param("longitude") Double longitude,
                                        @Param("latitude") Double latitude,
                                        @Param("distanceMeters") Integer distanceMeters);
}