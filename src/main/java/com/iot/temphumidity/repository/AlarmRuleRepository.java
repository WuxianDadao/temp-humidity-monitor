package com.iot.temphumidity.repository;

import com.iot.temphumidity.entity.AlarmRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Long> {
    
    /**
     * 根据规则名称查找报警规则
     */
    Optional<AlarmRule> findByRuleName(String ruleName);
    
    /**
     * 根据规则类型查找报警规则
     */
    List<AlarmRule> findByRuleType(String ruleType);
    
    /**
     * 根据传感器ID查找报警规则
     */
    @Query("SELECT a FROM AlarmRule a WHERE a.sensorTag.id = :sensorTagId")
    List<AlarmRule> findBySensorTagId(@Param("sensorTagId") Long sensorTagId);
    
    /**
     * 根据网关ID查找报警规则
     */
    @Query("SELECT a FROM AlarmRule a WHERE a.sensorTag.gateway.id = :gatewayId")
    List<AlarmRule> findByGatewayId(@Param("gatewayId") Long gatewayId);
    
    /**
     * 根据用户ID查找用户创建的报警规则
     */
    @Query("SELECT a FROM AlarmRule a WHERE a.createdBy = :userId")
    List<AlarmRule> findByCreatedBy(@Param("userId") Long userId);
    
    /**
     * 查找启用的报警规则
     */
    @Query("SELECT a FROM AlarmRule a WHERE a.enabled = true")
    List<AlarmRule> findEnabledAlarmRules();
    
    /**
     * 查找禁用的报警规则
     */
    @Query("SELECT a FROM AlarmRule a WHERE a.enabled = false")
    List<AlarmRule> findDisabledAlarmRules();
    
    /**
     * 根据规则状态查找报警规则
     */
    List<AlarmRule> findByRuleStatus(String ruleStatus);
    
    /**
     * 根据规则名称检查规则是否存在
     */
    boolean existsByRuleName(String ruleName);
    
    /**
     * 根据规则名称模糊查找
     */
    List<AlarmRule> findByRuleNameContainingIgnoreCase(String ruleName);
    
    /**
     * 根据规则描述模糊查找
     */
    List<AlarmRule> findByDescriptionContainingIgnoreCase(String description);
    
    /**
     * 根据严重等级查找报警规则
     */
    List<AlarmRule> findBySeverity(String severity);
    
    /**
     * 查找需要执行的报警规则
     */
    @Query("SELECT a FROM AlarmRule a WHERE a.enabled = true AND a.ruleStatus = 'ACTIVE'")
    List<AlarmRule> findActiveAlarmRules();
    
    /**
     * 统计报警规则数量
     */
    @Query("SELECT a.ruleType, COUNT(a) FROM AlarmRule a GROUP BY a.ruleType")
    List<Object[]> countAlarmRulesByType();
    
    @Query("SELECT a.severity, COUNT(a) FROM AlarmRule a GROUP BY a.severity")
    List<Object[]> countAlarmRulesBySeverity();
    
    @Query("SELECT a.enabled, COUNT(a) FROM AlarmRule a GROUP BY a.enabled")
    List<Object[]> countAlarmRulesByEnabledStatus();
    
    /**
     * 查找相同传感器和条件的重复规则
     */
    @Query("SELECT a FROM AlarmRule a WHERE a.sensorTag.id = :sensorTagId AND a.ruleType = :ruleType AND a.conditionOperator = :operator AND a.conditionValue = :value")
    List<AlarmRule> findDuplicateRules(@Param("sensorTagId") Long sensorTagId, 
                                       @Param("ruleType") String ruleType, 
                                       @Param("operator") String operator, 
                                       @Param("value") Double value);
}