package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.postgresql.AlarmHistoryEntity;
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
 * 报警历史Repository接口
 */
@Repository
public interface AlarmHistoryRepository extends JpaRepository<AlarmHistoryEntity, Long> {
    
    /**
     * 根据报警事件ID查找报警历史
     * @param alarmEventId 报警事件ID
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByAlarmEventId(Long alarmEventId);
    
    /**
     * 根据报警类型查找报警历史
     * @param alarmType 报警类型
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByAlarmType(String alarmType);
    
    /**
     * 根据设备ID查找报警历史
     * @param deviceId 设备ID
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByDeviceId(String deviceId);
    
    /**
     * 根据设备ID分页查找报警历史
     * @param deviceId 设备ID
     * @param pageable 分页信息
     * @return 分页报警历史
     */
    Page<AlarmHistoryEntity> findByDeviceId(String deviceId, Pageable pageable);
    
    /**
     * 根据传感器ID查找报警历史
     * @param sensorId 传感器ID
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findBySensorId(String sensorId);
    
    /**
     * 根据传感器ID分页查找报警历史
     * @param sensorId 传感器ID
     * @param pageable 分页信息
     * @return 分页报警历史
     */
    Page<AlarmHistoryEntity> findBySensorId(String sensorId, Pageable pageable);
    
    /**
     * 根据网关ID查找报警历史
     * @param gatewayId 网关ID
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByGatewayId(String gatewayId);
    
    /**
     * 根据网关ID分页查找报警历史
     * @param gatewayId 网关ID
     * @param pageable 分页信息
     * @return 分页报警历史
     */
    Page<AlarmHistoryEntity> findByGatewayId(String gatewayId, Pageable pageable);
    
    /**
     * 根据用户ID查找报警历史
     * @param userId 用户ID
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByUserId(Long userId);
    
    /**
     * 根据用户ID分页查找报警历史
     * @param userId 用户ID
     * @param pageable 分页信息
     * @return 分页报警历史
     */
    Page<AlarmHistoryEntity> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 根据报警状态查找报警历史
     * @param status 报警状态
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByStatus(String status);
    
    /**
     * 根据报警级别查找报警历史
     * @param level 报警级别
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByLevel(String level);
    
    /**
     * 根据时间范围查找报警历史
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据时间范围分页查找报警历史
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageable 分页信息
     * @return 分页报警历史
     */
    Page<AlarmHistoryEntity> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    
    /**
     * 查找未处理的报警历史
     * @return 未处理报警历史列表
     */
    List<AlarmHistoryEntity> findByProcessedFalse();
    
    /**
     * 查找已处理的报警历史
     * @return 已处理报警历史列表
     */
    List<AlarmHistoryEntity> findByProcessedTrue();
    
    /**
     * 根据时间范围和设备ID查找报警历史
     * @param deviceId 设备ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 报警历史列表
     */
    List<AlarmHistoryEntity> findByDeviceIdAndCreatedAtBetween(String deviceId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计指定设备的报警次数
     * @param deviceId 设备ID
     * @return 报警次数
     */
    @Query("SELECT COUNT(a) FROM AlarmHistoryEntity a WHERE a.deviceId = :deviceId")
    Long countByDeviceId(@Param("deviceId") String deviceId);
    
    /**
     * 统计指定时间范围内的报警次数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 报警次数
     */
    @Query("SELECT COUNT(a) FROM AlarmHistoryEntity a WHERE a.createdAt BETWEEN :startTime AND :endTime")
    Long countByTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查找最新的报警历史
     * @param limit 限制数量
     * @return 最新的报警历史列表
     */
    @Query("SELECT a FROM AlarmHistoryEntity a ORDER BY a.createdAt DESC")
    List<AlarmHistoryEntity> findLatestAlarms(@Param("limit") int limit);
    
    /**
     * 查找指定时间段内的高频报警设备
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param threshold 报警次数阈值
     * @return 设备ID列表
     */
    @Query("SELECT a.deviceId FROM AlarmHistoryEntity a " +
           "WHERE a.createdAt BETWEEN :startTime AND :endTime " +
           "GROUP BY a.deviceId " +
           "HAVING COUNT(a) >= :threshold")
    List<String> findHighFrequencyAlarmDevices(@Param("startTime") LocalDateTime startTime, 
                                              @Param("endTime") LocalDateTime endTime,
                                              @Param("threshold") Long threshold);
}