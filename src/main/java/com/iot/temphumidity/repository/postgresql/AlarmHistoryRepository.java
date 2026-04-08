package com.iot.temphumidity.repository.postgresql;

import com.iot.temphumidity.entity.postgresql.AlarmHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 报警历史数据访问接口
 */
@Repository
public interface AlarmHistoryRepository extends JpaRepository<AlarmHistoryEntity, Long>, 
                                                JpaSpecificationExecutor<AlarmHistoryEntity> {
    
    /**
     * 根据传感器ID查找报警记录
     */
    List<AlarmHistoryEntity> findBySensorId(String sensorId);
    
    /**
     * 根据传感器ID和状态查找报警记录
     */
    List<AlarmHistoryEntity> findBySensorIdAndStatus(String sensorId, String status);
    
    /**
     * 根据网关ID查找报警记录
     */
    List<AlarmHistoryEntity> findByGatewayId(Long gatewayId);
    
    /**
     * 根据设备ID查找报警记录
     */
    List<AlarmHistoryEntity> findByDeviceId(Long deviceId);
    
    /**
     * 根据报警配置ID查找报警记录
     */
    List<AlarmHistoryEntity> findByAlarmConfigId(Long alarmConfigId);
    
    /**
     * 根据报警类型查找报警记录
     */
    List<AlarmHistoryEntity> findByAlarmType(String alarmType);
    
    /**
     * 根据严重程度查找报警记录
     */
    List<AlarmHistoryEntity> findBySeverity(String severity);
    
    /**
     * 根据状态查找报警记录
     */
    List<AlarmHistoryEntity> findByStatus(String status);
    
    /**
     * 根据确认状态查找报警记录
     */
    List<AlarmHistoryEntity> findByAcknowledgedTimeIsNull();
    
    /**
     * 根据解决状态查找报警记录
     */
    List<AlarmHistoryEntity> findByResolveTimeIsNull();
    
    /**
     * 查找活动报警（未解决）
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.resolveTime IS NULL")
    List<AlarmHistoryEntity> findActiveAlarms();
    
    /**
     * 查找未确认报警
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.acknowledgedTime IS NULL AND a.resolveTime IS NULL")
    List<AlarmHistoryEntity> findUnacknowledgedAlarms();
    
    /**
     * 根据传感器ID查找活动报警
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.sensorId = :sensorId AND a.resolveTime IS NULL")
    List<AlarmHistoryEntity> findActiveAlarmsBySensorId(@Param("sensorId") String sensorId);
    
    /**
     * 根据网关ID查找活动报警
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.gatewayId = :gatewayId AND a.resolveTime IS NULL")
    List<AlarmHistoryEntity> findActiveAlarmsByGatewayId(@Param("gatewayId") Long gatewayId);
    
    /**
     * 根据时间范围查找报警记录
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.triggerTime BETWEEN :startTime AND :endTime")
    List<AlarmHistoryEntity> findAlarmsByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据报警配置ID查找报警记录
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.alarmConfigId = :configId")
    List<AlarmHistoryEntity> findAlarmsByConfigId(@Param("configId") Long configId);
    
    /**
     * 查找过期报警记录
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.triggerTime < :cutoffTime")
    List<AlarmHistoryEntity> findExpiredAlarms(@Param("cutoffTime") LocalDateTime cutoffTime);
    
    /**
     * 统计活动报警数量
     */
    @Query("SELECT COUNT(a) FROM AlarmHistoryEntity a WHERE a.resolveTime IS NULL")
    long countActiveAlarms();
    
    /**
     * 统计未确认报警数量
     */
    @Query("SELECT COUNT(a) FROM AlarmHistoryEntity a WHERE a.acknowledgedTime IS NULL AND a.resolveTime IS NULL")
    long countUnacknowledgedAlarms();
    
    /**
     * 获取报警类型统计
     */
    @Query("SELECT a.alarmType, COUNT(a) FROM AlarmHistoryEntity a GROUP BY a.alarmType ORDER BY COUNT(a) DESC")
    List<Object[]> getAlarmTypeStatistics();
    
    /**
     * 获取严重程度统计
     */
    @Query("SELECT a.severity, COUNT(a) FROM AlarmHistoryEntity a GROUP BY a.severity ORDER BY COUNT(a) DESC")
    List<Object[]> getSeverityStatistics();
    
    /**
     * 获取每日报警统计
     */
    @Query("SELECT DATE_TRUNC('day', a.triggerTime), COUNT(a) FROM AlarmHistoryEntity a " +
           "WHERE a.triggerTime >= :startDate GROUP BY DATE_TRUNC('day', a.triggerTime) " +
           "ORDER BY DATE_TRUNC('day', a.triggerTime)")
    List<Object[]> getAlarmsByDate(@Param("startDate") LocalDateTime startDate);
    
    /**
     * 获取每小时报警统计
     */
    @Query("SELECT DATE_TRUNC('hour', a.triggerTime), COUNT(a) FROM AlarmHistoryEntity a " +
           "WHERE a.triggerTime >= :startTime GROUP BY DATE_TRUNC('hour', a.triggerTime) " +
           "ORDER BY DATE_TRUNC('hour', a.triggerTime)")
    List<Object[]> getAlarmsByHour(@Param("startTime") LocalDateTime startTime);
    
    /**
     * 获取平均解决时间（秒）
     */
    @Query(value = "SELECT AVG(EXTRACT(EPOCH FROM (a.resolve_time - a.trigger_time))) FROM alarm_history a " +
           "WHERE a.resolve_time IS NOT NULL", nativeQuery = true)
    Double getAverageResolveTime();
    
    /**
     * 搜索报警记录
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE " +
           "(:sensorId IS NULL OR a.sensorId LIKE %:sensorId%) AND " +
           "(:gatewayId IS NULL OR a.gatewayId = :gatewayId) AND " +
           "(:deviceId IS NULL OR a.deviceId = :deviceId) AND " +
           "(:alarmType IS NULL OR a.alarmType = :alarmType) AND " +
           "(:severity IS NULL OR a.severity = :severity) AND " +
           "(:resolved IS NULL OR (:resolved = true AND a.resolveTime IS NOT NULL) OR (:resolved = false AND a.resolveTime IS NULL)) AND " +
           "(:acknowledged IS NULL OR (:acknowledged = true AND a.acknowledgedTime IS NOT NULL) OR (:acknowledged = false AND a.acknowledgedTime IS NULL)) AND " +
           "(:startTime IS NULL OR a.triggerTime >= :startTime) AND " +
           "(:endTime IS NULL OR a.triggerTime <= :endTime)")
    Page<AlarmHistoryEntity> searchAlarmHistories(@Param("sensorId") String sensorId,
                                                  @Param("gatewayId") Long gatewayId,
                                                  @Param("deviceId") Long deviceId,
                                                  @Param("alarmType") String alarmType,
                                                  @Param("severity") String severity,
                                                  @Param("resolved") Boolean resolved,
                                                  @Param("acknowledged") Boolean acknowledged,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime,
                                                  Pageable pageable);
    
    /**
     * 获取报警趋势数据
     */
    @Query("SELECT " +
           "CASE WHEN :groupingType = 'hour' THEN DATE_TRUNC('hour', a.triggerTime) " +
           "     WHEN :groupingType = 'day' THEN DATE_TRUNC('day', a.triggerTime) " +
           "     WHEN :groupingType = 'week' THEN DATE_TRUNC('week', a.triggerTime) " +
           "     ELSE DATE_TRUNC('month', a.triggerTime) END, " +
           "a.alarmType, COUNT(a) " +
           "FROM AlarmHistoryEntity a " +
           "WHERE a.triggerTime BETWEEN :startTime AND :endTime " +
           "GROUP BY " +
           "CASE WHEN :groupingType = 'hour' THEN DATE_TRUNC('hour', a.triggerTime) " +
           "     WHEN :groupingType = 'day' THEN DATE_TRUNC('day', a.triggerTime) " +
           "     WHEN :groupingType = 'week' THEN DATE_TRUNC('week', a.triggerTime) " +
           "     ELSE DATE_TRUNC('month', a.triggerTime) END, a.alarmType " +
           "ORDER BY 1, 2")
    List<Object[]> getAlarmTrendData(@Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime,
                                     @Param("groupingType") String groupingType);
    
    /**
     * 获取报警性能指标
     */
    @Query(value = "SELECT " +
           "COUNT(*) as totalAlarms, " +
           "SUM(CASE WHEN a.resolve_time IS NOT NULL THEN 1 ELSE 0 END) as resolvedAlarms, " +
           "AVG(CASE WHEN a.resolve_time IS NOT NULL THEN EXTRACT(EPOCH FROM (a.resolve_time - a.trigger_time)) END) as avgResolveTime, " +
           "AVG(CASE WHEN a.acknowledged_time IS NOT NULL THEN EXTRACT(EPOCH FROM (a.acknowledged_time - a.trigger_time)) END) as avgAcknowledgeTime, " +
           "COUNT(DISTINCT a.sensor_id) as affectedSensors " +
           "FROM alarm_history a " +
           "WHERE a.trigger_time BETWEEN :startTime AND :endTime", nativeQuery = true)
    Object getAlarmPerformanceMetrics(@Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查找最近报警记录
     */
    @Query("SELECT a FROM AlarmHistoryEntity a ORDER BY a.triggerTime DESC")
    List<AlarmHistoryEntity> findRecentAlarms(Pageable pageable);
    
    /**
     * 查找最严重报警记录
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.severity IN ('CRITICAL', 'HIGH') AND a.resolveTime IS NULL ORDER BY a.triggerTime DESC")
    List<AlarmHistoryEntity> findCriticalAlarms();
    
    /**
     * 查找长时间未确认报警
     */
    @Query("SELECT a FROM AlarmHistoryEntity a WHERE a.acknowledgedTime IS NULL AND a.triggerTime < :cutoffTime")
    List<AlarmHistoryEntity> findStaleUnacknowledgedAlarms(@Param("cutoffTime") LocalDateTime cutoffTime);
}