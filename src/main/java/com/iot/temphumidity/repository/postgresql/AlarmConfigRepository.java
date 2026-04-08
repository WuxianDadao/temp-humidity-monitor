package com.iot.temphumidity.repository.postgresql;

import com.iot.temphumidity.entity.postgresql.AlarmConfigEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 报警配置数据访问接口
 */
@Repository
public interface AlarmConfigRepository extends JpaRepository<AlarmConfigEntity, Long> {
    
    List<AlarmConfigEntity> findBySensorId(String sensorId);
    
    List<AlarmConfigEntity> findByGatewayId(Long gatewayId);
    
    List<AlarmConfigEntity> findByDeviceId(Long deviceId);
    
    List<AlarmConfigEntity> findByAlarmType(String alarmType);
    
    List<AlarmConfigEntity> findByEnabled(Boolean enabled);
    
    Page<AlarmConfigEntity> findByEnabled(Boolean enabled, Pageable pageable);
    
    @Query("SELECT a FROM AlarmConfigEntity a WHERE a.enabled = true AND a.sensorId = :sensorId")
    List<AlarmConfigEntity> findActiveAlarmsBySensorId(@Param("sensorId") String sensorId);
    
    @Query("SELECT a FROM AlarmConfigEntity a WHERE a.enabled = true AND a.gatewayId = :gatewayId")
    List<AlarmConfigEntity> findActiveAlarmsByGatewayId(@Param("gatewayId") Long gatewayId);
    
    @Query("SELECT a FROM AlarmConfigEntity a WHERE a.enabled = true AND a.alarmType = :alarmType")
    List<AlarmConfigEntity> findActiveAlarmsByType(@Param("alarmType") String alarmType);
    
    @Query("SELECT a FROM AlarmConfigEntity a WHERE a.enabled = true AND " +
           "(a.sensorId = :sensorId OR a.sensorId IS NULL) AND " +
           "(a.gatewayId = :gatewayId OR a.gatewayId IS NULL) AND " +
           "(a.deviceId = :deviceId OR a.deviceId IS NULL)")
    List<AlarmConfigEntity> findApplicableAlarms(@Param("sensorId") String sensorId,
                                                @Param("gatewayId") Long gatewayId,
                                                @Param("deviceId") Long deviceId);
    
    @Query("SELECT DISTINCT a.alarmType FROM AlarmConfigEntity a WHERE a.enabled = true")
    List<String> findDistinctActiveAlarmTypes();
    
    @Query("SELECT a FROM AlarmConfigEntity a WHERE " +
           "(:name IS NULL OR a.name LIKE %:name%) AND " +
           "(:alarmType IS NULL OR a.alarmType = :alarmType) AND " +
           "(:severity IS NULL OR a.severity = :severity) AND " +
           "(:enabled IS NULL OR a.enabled = :enabled)")
    Page<AlarmConfigEntity> searchAlarms(@Param("name") String name,
                                        @Param("alarmType") String alarmType,
                                        @Param("severity") String severity,
                                        @Param("enabled") Boolean enabled,
                                        Pageable pageable);
    
    @Query("SELECT COUNT(a) FROM AlarmConfigEntity a WHERE a.enabled = true")
    long countActiveAlarms();
    
    @Query("SELECT new map(a.alarmType as alarmType, COUNT(a) as count) " +
           "FROM AlarmConfigEntity a WHERE a.enabled = true GROUP BY a.alarmType")
    List<Object[]> countActiveAlarmsByType();
    
    @Query("SELECT new map(a.severity as severity, COUNT(a) as count) " +
           "FROM AlarmConfigEntity a WHERE a.enabled = true GROUP BY a.severity")
    List<Object[]> countActiveAlarmsBySeverity();
    
    @Query("SELECT a FROM AlarmConfigEntity a WHERE " +
           "a.enabled = true AND " +
           "(a.sensorId = :sensorId OR a.sensorId IS NULL)")
    List<AlarmConfigEntity> findNotificationAlarmsForSensor(@Param("sensorId") String sensorId);
    
    @Query("SELECT a FROM AlarmConfigEntity a WHERE a.enabled = true AND " +
           "(a.thresholdMin IS NOT NULL OR a.thresholdMax IS NOT NULL)")
    List<AlarmConfigEntity> findThresholdBasedAlarms();
    
    Optional<AlarmConfigEntity> findByName(String name);
    
    boolean existsByName(String name);
}