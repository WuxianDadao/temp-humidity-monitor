package com.iot.temphumidity.service;

import com.iot.temphumidity.dto.alarm.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 报警管理服务接口
 */
public interface AlarmService {

    // ===== 报警规则管理 =====
    
    /**
     * 创建报警规则
     * @param ruleCreateDTO 报警规则创建信息
     * @return 创建后的报警规则信息
     */
    AlarmRuleDTO createAlarmRule(AlarmRuleCreateDTO ruleCreateDTO);

    /**
     * 更新报警规则
     * @param ruleId 报警规则ID
     * @param ruleUpdateDTO 报警规则更新信息
     * @return 更新后的报警规则信息
     */
    AlarmRuleDTO updateAlarmRule(Long ruleId, AlarmRuleUpdateDTO ruleUpdateDTO);

    /**
     * 获取报警规则详情
     * @param ruleId 报警规则ID
     * @return 报警规则详情
     */
    AlarmRuleDTO getAlarmRuleById(Long ruleId);

    /**
     * 分页查询报警规则列表
     * @param pageable 分页参数
     * @return 报警规则分页列表
     */
    Page<AlarmRuleDTO> getAlarmRules(Pageable pageable);

    /**
     * 根据设备ID查询报警规则
     * @param deviceId 设备ID
     * @param pageable 分页参数
     * @return 报警规则分页列表
     */
    Page<AlarmRuleDTO> getAlarmRulesByDevice(Long deviceId, Pageable pageable);

    /**
     * 根据传感器标签ID查询报警规则
     * @param tagId 传感器标签ID
     * @param pageable 分页参数
     * @return 报警规则分页列表
     */
    Page<AlarmRuleDTO> getAlarmRulesByTag(Long tagId, Pageable pageable);

    /**
     * 删除报警规则
     * @param ruleId 报警规则ID
     */
    void deleteAlarmRule(Long ruleId);

    /**
     * 启用/禁用报警规则
     * @param ruleId 报警规则ID
     * @param enabled 是否启用
     */
    void toggleAlarmRule(Long ruleId, boolean enabled);

    // ===== 报警记录管理 =====
    
    /**
     * 创建报警记录
     * @param alarmCreateDTO 报警记录创建信息
     * @return 创建后的报警记录信息
     */
    AlarmDTO createAlarm(AlarmCreateDTO alarmCreateDTO);

    /**
     * 获取报警记录详情
     * @param alarmId 报警记录ID
     * @return 报警记录详情
     */
    AlarmDTO getAlarmById(Long alarmId);

    /**
     * 分页查询报警记录列表
     * @param pageable 分页参数
     * @return 报警记录分页列表
     */
    Page<AlarmDTO> getAlarms(Pageable pageable);

    /**
     * 根据设备ID分页查询报警记录
     * @param deviceId 设备ID
     * @param pageable 分页参数
     * @return 报警记录分页列表
     */
    Page<AlarmDTO> getAlarmsByDevice(Long deviceId, Pageable pageable);

    /**
     * 根据传感器标签ID分页查询报警记录
     * @param tagId 传感器标签ID
     * @param pageable 分页参数
     * @return 报警记录分页列表
     */
    Page<AlarmDTO> getAlarmsByTag(Long tagId, Pageable pageable);

    /**
     * 根据报警规则ID分页查询报警记录
     * @param ruleId 报警规则ID
     * @param pageable 分页参数
     * @return 报警记录分页列表
     */
    Page<AlarmDTO> getAlarmsByRule(Long ruleId, Pageable pageable);

    /**
     * 根据状态分页查询报警记录
     * @param status 报警状态 (PENDING, PROCESSING, RESOLVED, IGNORED)
     * @param pageable 分页参数
     * @return 报警记录分页列表
     */
    Page<AlarmDTO> getAlarmsByStatus(String status, Pageable pageable);

    /**
     * 更新报警状态
     * @param alarmId 报警记录ID
     * @param status 新状态
     * @param resolvedBy 处理人ID
     * @param resolutionNotes 处理说明
     */
    void updateAlarmStatus(Long alarmId, String status, Long resolvedBy, String resolutionNotes);

    /**
     * 批量确认报警
     * @param alarmIds 报警记录ID列表
     * @param resolvedBy 处理人ID
     */
    void batchAcknowledgeAlarms(List<Long> alarmIds, Long resolvedBy);

    /**
     * 获取未处理报警数量
     * @return 未处理报警数量
     */
    long getPendingAlarmCount();

    /**
     * 获取最近报警记录
     * @param limit 限制数量
     * @return 最近报警记录列表
     */
    List<AlarmDTO> getRecentAlarms(int limit);

    // ===== 报警触发和检查 =====
    
    /**
     * 检查传感器数据并触发报警
     * @param deviceId 设备ID
     * @param tagId 传感器标签ID
     * @param temperature 温度值
     * @param humidity 湿度值
     * @param timestamp 时间戳
     */
    void checkAndTriggerAlarm(Long deviceId, Long tagId, Double temperature, Double humidity, String timestamp);

    /**
     * 处理MQTT数据并触发报警
     * @param mqttData MQTT数据
     */
    void processMqttDataForAlarm(String mqttData);

    /**
     * 清理过期报警记录
     * @param days 保留天数
     * @return 清理的记录数
     */
    int cleanupExpiredAlarms(int days);

    // ===== 报警事件管理 =====
    
    /**
     * 解决报警事件
     * @param eventId 报警事件ID
     * @param resolveDTO 解决信息
     * @return 解决后的报警事件信息
     */
    AlarmEventDTO resolveAlarmEvent(Long eventId, ResolveAlarmDTO resolveDTO);

    /**
     * 发送报警通知
     * @param eventId 报警事件ID
     * @param notificationDTO 通知信息
     * @return 发送的报警通知信息
     */
    AlarmNotificationDTO sendAlarmNotification(Long eventId, SendNotificationDTO notificationDTO);

    /**
     * 获取报警通知
     * @param eventId 报警事件ID
     * @return 报警事件的所有通知记录
     */
    List<AlarmNotificationDTO> getAlarmNotifications(Long eventId);

    /**
     * 检查报警
     * @param checkDTO 检查参数
     * @return 检查结果列表
     */
    List<AlarmCheckResultDTO> checkAlarms(CheckAlarmsDTO checkDTO);

    /**
     * 获取报警统计信息
     * @param startTime 统计开始时间
     * @param endTime 统计结束时间
     * @return 报警统计信息
     */
    AlarmStatsDTO getAlarmStats(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 确认报警事件
     * @param eventId 报警事件ID
     * @param acknowledgeDTO 确认信息
     * @return 确认后的报警事件信息
     */
    AlarmEventDTO acknowledgeAlarmEvent(Long eventId, AcknowledgeAlarmDTO acknowledgeDTO);

    /**
     * 测试报警规则
     * @param ruleId 报警规则ID
     * @param testDTO 测试参数
     * @return 测试结果
     */
    AlarmTestResultDTO testAlarmRule(Long ruleId, TestAlarmRuleDTO testDTO);
    
    /**
     * 检查报警规则（针对TDengine传感器数据）
     * @param sensorData 传感器数据
     */
    void checkAlarmRules(Object sensorData);
    
    /**
     * 根据时间范围获取报警事件
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 报警事件列表
     */
    List<AlarmEventDTO> getAlarmEventsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
}