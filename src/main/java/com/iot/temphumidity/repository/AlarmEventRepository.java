package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.AlarmEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {
    
    /**
     * 根据报警事件状态查找事件
     */
    List<AlarmEvent> findByEventStatus(String eventStatus);
    
    /**
     * 根据传感器ID查找报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.sensorTag.id = :sensorTagId")
    List<AlarmEvent> findBySensorTagId(@Param("sensorTagId") Long sensorTagId);
    
    /**
     * 根据网关ID查找报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.sensorTag.gateway.id = :gatewayId")
    List<AlarmEvent> findByGatewayId(@Param("gatewayId") Long gatewayId);
    
    /**
     * 根据报警规则ID查找报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.alarmRule.id = :alarmRuleId")
    List<AlarmEvent> findByAlarmRuleId(@Param("alarmRuleId") Long alarmRuleId);
    
    /**
     * 根据用户ID查找用户相关的报警事件
     */
    @Query("SELECT a FROM AlarmEvent a JOIN a.sensorTag s JOIN s.gateway g JOIN g.users u WHERE u.id = :userId")
    List<AlarmEvent> findByUserId(@Param("userId") Long userId);
    
    /**
     * 根据严重等级查找报警事件
     */
    List<AlarmEvent> findBySeverity(String severity);
    
    /**
     * 查找未确认的报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.acknowledged = false")
    List<AlarmEvent> findUnacknowledgedAlarmEvents();
    
    /**
     * 查找未解决的报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.resolved = false")
    List<AlarmEvent> findUnresolvedAlarmEvents();
    
    /**
     * 查找已确认但未解决的报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.acknowledged = true AND a.resolved = false")
    List<AlarmEvent> findAcknowledgedUnresolvedAlarmEvents();
    
    /**
     * 查找已解决但未确认的报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.resolved = true AND a.acknowledged = false")
    List<AlarmEvent> findResolvedUnacknowledgedAlarmEvents();
    
    /**
     * 根据时间范围查找报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.eventTime BETWEEN :startTime AND :endTime")
    List<AlarmEvent> findByEventTimeBetween(@Param("startTime") LocalDateTime startTime, 
                                           @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计报警事件数量
     */
    @Query("SELECT a.eventStatus, COUNT(a) FROM AlarmEvent a GROUP BY a.eventStatus")
    List<Object[]> countAlarmEventsByStatus();
    
    @Query("SELECT a.severity, COUNT(a) FROM AlarmEvent a GROUP BY a.severity")
    List<Object[]> countAlarmEventsBySeverity();
    
    @Query("SELECT a.acknowledged, COUNT(a) FROM AlarmEvent a GROUP BY a.acknowledged")
    List<Object[]> countAlarmEventsByAcknowledged();
    
    @Query("SELECT a.resolved, COUNT(a) FROM AlarmEvent a GROUP BY a.resolved")
    List<Object[]> countAlarmEventsByResolved();
    
    /**
     * 查找最近发生的报警事件
     */
    @Query("SELECT a FROM AlarmEvent a ORDER BY a.eventTime DESC")
    List<AlarmEvent> findRecentAlarmEvents();
    
    /**
     * 查找指定时间段内的未确认报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.acknowledged = false AND a.eventTime BETWEEN :startTime AND :endTime")
    List<AlarmEvent> findRecentUnacknowledgedAlarmEvents(@Param("startTime") LocalDateTime startTime, 
                                                        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查找持续时间最长的未解决报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.resolved = false ORDER BY a.eventTime ASC")
    List<AlarmEvent> findLongestUnresolvedAlarmEvents();
    
    /**
     * 查找重复报警事件（相同传感器、规则、时间范围内）
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.sensorTag.id = :sensorTagId AND a.alarmRule.id = :alarmRuleId AND a.eventTime BETWEEN :startTime AND :endTime")
    List<AlarmEvent> findDuplicateAlarmEvents(@Param("sensorTagId") Long sensorTagId, 
                                             @Param("alarmRuleId") Long alarmRuleId, 
                                             @Param("startTime") LocalDateTime startTime, 
                                             @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据确认时间查找报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.acknowledgeTime BETWEEN :startTime AND :endTime")
    List<AlarmEvent> findByAcknowledgeTimeBetween(@Param("startTime") LocalDateTime startTime, 
                                                 @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据解决时间查找报警事件
     */
    @Query("SELECT a FROM AlarmEvent a WHERE a.resolveTime BETWEEN :startTime AND :endTime")
    List<AlarmEvent> findByResolveTimeBetween(@Param("startTime") LocalDateTime startTime, 
                                             @Param("endTime") LocalDateTime endTime);
}