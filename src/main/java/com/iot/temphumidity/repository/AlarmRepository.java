package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.Alarm;
import com.iot.temphumidity.enums.AlarmStatus;
import com.iot.temphumidity.enums.AlarmSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 报警记录Repository接口
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    
    /**
     * 根据设备ID查找报警记录
     */
    List<Alarm> findByDeviceId(Long deviceId);
    
    /**
     * 根据传感器标签ID查找报警记录
     */
    List<Alarm> findBySensorTagId(Long sensorTagId);
    
    /**
     * 根据报警规则ID查找报警记录
     */
    List<Alarm> findByAlarmRuleId(Long alarmRuleId);
    
    /**
     * 根据报警状态查找报警记录
     */
    List<Alarm> findByStatus(AlarmStatus status);
    
    /**
     * 根据严重程度查找报警记录
     */
    List<Alarm> findBySeverity(AlarmSeverity severity);
    
    /**
     * 查找未处理的报警记录
     */
    @Query("SELECT a FROM Alarm a WHERE a.status = 'PENDING' OR a.status = 'ACTIVE'")
    List<Alarm> findUnresolvedAlarms();
    
    /**
     * 查找已处理的报警记录
     */
    @Query("SELECT a FROM Alarm a WHERE a.status = 'RESOLVED' OR a.status = 'CLOSED'")
    List<Alarm> findResolvedAlarms();
    
    /**
     * 根据时间范围查找报警记录
     */
    @Query("SELECT a FROM Alarm a WHERE a.createdAt BETWEEN :startTime AND :endTime")
    List<Alarm> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                               @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查找最近N小时内的报警记录
     */
    @Query("SELECT a FROM Alarm a WHERE a.createdAt >= :since")
    List<Alarm> findRecentAlarms(@Param("since") LocalDateTime since);
    
    /**
     * 统计各状态报警数量
     */
    @Query("SELECT a.status, COUNT(a) FROM Alarm a GROUP BY a.status")
    List<Object[]> countAlarmsByStatus();
    
    /**
     * 统计各严重程度报警数量
     */
    @Query("SELECT a.severity, COUNT(a) FROM Alarm a GROUP BY a.severity")
    List<Object[]> countAlarmsBySeverity();
    
    /**
     * 查找需要通知的报警（未发送通知的）
     */
    @Query("SELECT a FROM Alarm a WHERE a.notificationSent = false")
    List<Alarm> findAlarmsNeedingNotification();
    
    /**
     * 根据确认状态查找报警
     */
    List<Alarm> findByAcknowledged(boolean acknowledged);
    
    /**
     * 查找未确认的报警
     */
    List<Alarm> findByAcknowledgedFalse();
    
    /**
     * 查找已确认的报警
     */
    List<Alarm> findByAcknowledgedTrue();
    
    /**
     * 根据处理人ID查找报警记录
     */
    List<Alarm> findByProcessedBy(Long processedBy);
    
    /**
     * 查找特定设备上指定规则的最新报警
     */
    @Query("SELECT a FROM Alarm a WHERE a.deviceId = :deviceId AND a.alarmRuleId = :ruleId ORDER BY a.createdAt DESC")
    List<Alarm> findLatestAlarmByDeviceAndRule(@Param("deviceId") Long deviceId, 
                                              @Param("ruleId") Long ruleId);
    
    /**
     * 统计指定时间段内各设备的报警频率
     */
    @Query("SELECT a.deviceId, COUNT(a) FROM Alarm a WHERE a.createdAt BETWEEN :startTime AND :endTime GROUP BY a.deviceId")
    List<Object[]> countAlarmsByDeviceInPeriod(@Param("startTime") LocalDateTime startTime,
                                              @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查找重复报警（相同设备、相同规则、相同状态、在短时间内发生）
     */
    @Query("SELECT a1 FROM Alarm a1, Alarm a2 WHERE a1.deviceId = a2.deviceId AND a1.alarmRuleId = a2.alarmRuleId " +
           "AND a1.status = a2.status AND a1.id != a2.id AND ABS(TIMESTAMPDIFF(MINUTE, a1.createdAt, a2.createdAt)) < :minutes")
    List<Alarm> findDuplicateAlarmsWithinMinutes(@Param("minutes") int minutes);
    
    /**
     * 根据报警类型查找报警记录
     */
    @Query("SELECT a FROM Alarm a WHERE a.alarmType = :alarmType")
    List<Alarm> findByAlarmType(@Param("alarmType") String alarmType);
    
    /**
     * 根据标签查找报警记录
     */
    @Query("SELECT a FROM Alarm a WHERE a.tags LIKE %:tag%")
    List<Alarm> findByTag(@Param("tag") String tag);
    
    /**
     * 查找持续时间超过阈值的报警
     */
    @Query("SELECT a FROM Alarm a WHERE a.durationMinutes > :threshold")
    List<Alarm> findByDurationExceeding(@Param("threshold") int threshold);
}