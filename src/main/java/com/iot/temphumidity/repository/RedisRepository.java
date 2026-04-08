package com.iot.temphumidity.repository;

import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis操作Repository
 * 用于缓存、实时状态、会话管理等
 */
@Repository
public class RedisRepository {
    
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    
    public RedisRepository(RedisTemplate<String, Object> redisTemplate, 
                          StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    
    // ==================== 通用操作方法 ====================
    
    /**
     * 设置键值对
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    /**
     * 设置键值对并设置过期时间
     */
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }
    
    /**
     * 获取值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 获取字符串值
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
    
    /**
     * 删除键
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
    
    /**
     * 批量删除键
     */
    public Long deleteKeys(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }
    
    /**
     * 检查键是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    
    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }
    
    /**
     * 获取过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }
    
    // ==================== 设备在线状态管理 ====================
    
    private static final String DEVICE_ONLINE_KEY = "device:online:";
    private static final String GATEWAY_ONLINE_KEY = "gateway:online:";
    private static final String DEVICE_LAST_DATA_KEY = "device:last_data:";
    private static final String DEVICE_HEARTBEAT_KEY = "device:heartbeat:";
    
    /**
     * 更新设备在线状态
     */
    public void updateDeviceOnlineStatus(String deviceId, boolean online) {
        String key = DEVICE_ONLINE_KEY + deviceId;
        if (online) {
            redisTemplate.opsForValue().set(key, "1", Duration.ofMinutes(5));
        } else {
            redisTemplate.delete(key);
        }
    }
    
    /**
     * 检查设备是否在线
     */
    public boolean isDeviceOnline(String deviceId) {
        String key = DEVICE_ONLINE_KEY + deviceId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    /**
     * 获取所有在线设备
     */
    public Set<String> getAllOnlineDevices() {
        Set<String> keys = redisTemplate.keys(DEVICE_ONLINE_KEY + "*");
        if (keys == null) {
            return new HashSet<>();
        }
        return keys.stream()
                .map(key -> key.substring(DEVICE_ONLINE_KEY.length()))
                .collect(Collectors.toSet());
    }
    
    /**
     * 更新网关在线状态
     */
    public void updateGatewayOnlineStatus(String gatewayId, boolean online) {
        String key = GATEWAY_ONLINE_KEY + gatewayId;
        if (online) {
            redisTemplate.opsForValue().set(key, "1", Duration.ofMinutes(5));
        } else {
            redisTemplate.delete(key);
        }
    }
    
    /**
     * 检查网关是否在线
     */
    public boolean isGatewayOnline(String gatewayId) {
        String key = GATEWAY_ONLINE_KEY + gatewayId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    /**
     * 更新设备最新数据
     */
    public void updateDeviceLastData(String deviceId, Map<String, Object> data) {
        String key = DEVICE_LAST_DATA_KEY + deviceId;
        redisTemplate.opsForHash().putAll(key, data);
        redisTemplate.expire(key, Duration.ofMinutes(10));
    }
    
    /**
     * 获取设备最新数据
     */
    public Map<Object, Object> getDeviceLastData(String deviceId) {
        String key = DEVICE_LAST_DATA_KEY + deviceId;
        return redisTemplate.opsForHash().entries(key);
    }
    
    /**
     * 更新设备心跳
     */
    public void updateDeviceHeartbeat(String deviceId) {
        String key = DEVICE_HEARTBEAT_KEY + deviceId;
        redisTemplate.opsForValue().set(key, System.currentTimeMillis(), Duration.ofMinutes(5));
    }
    
    /**
     * 获取设备最后心跳时间
     */
    public Long getDeviceLastHeartbeat(String deviceId) {
        String key = DEVICE_HEARTBEAT_KEY + deviceId;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value.toString()) : null;
    }
    
    // ==================== 传感器数据缓存 ====================
    
    private static final String SENSOR_DATA_CACHE_KEY = "sensor:data:";
    private static final String SENSOR_STATS_CACHE_KEY = "sensor:stats:";
    
    /**
     * 缓存传感器数据
     */
    public void cacheSensorData(String sensorId, List<Map<String, Object>> data) {
        String key = SENSOR_DATA_CACHE_KEY + sensorId;
        redisTemplate.opsForValue().set(key, data, Duration.ofMinutes(5));
    }
    
    /**
     * 获取缓存的传感器数据
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getCachedSensorData(String sensorId) {
        String key = SENSOR_DATA_CACHE_KEY + sensorId;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? (List<Map<String, Object>>) value : new ArrayList<>();
    }
    
    /**
     * 缓存传感器统计信息
     */
    public void cacheSensorStats(String sensorId, Map<String, Object> stats) {
        String key = SENSOR_STATS_CACHE_KEY + sensorId;
        redisTemplate.opsForHash().putAll(key, stats);
        redisTemplate.expire(key, Duration.ofMinutes(10));
    }
    
    /**
     * 获取缓存的传感器统计信息
     */
    public Map<Object, Object> getCachedSensorStats(String sensorId) {
        String key = SENSOR_STATS_CACHE_KEY + sensorId;
        return redisTemplate.opsForHash().entries(key);
    }
    
    // ==================== 报警缓存管理 ====================
    
    private static final String ALARM_CACHE_KEY = "alarm:";
    private static final String ALARM_ACTIVE_KEY = "alarm:active:";
    private static final String ALARM_HISTORY_KEY = "alarm:history:";
    
    /**
     * 缓存报警信息
     */
    public void cacheAlarm(String alarmId, Map<String, Object> alarmInfo) {
        String key = ALARM_CACHE_KEY + alarmId;
        redisTemplate.opsForHash().putAll(key, alarmInfo);
        redisTemplate.expire(key, Duration.ofHours(1));
    }
    
    /**
     * 获取缓存的报警信息
     */
    public Map<Object, Object> getCachedAlarm(String alarmId) {
        String key = ALARM_CACHE_KEY + alarmId;
        return redisTemplate.opsForHash().entries(key);
    }
    
    /**
     * 添加活跃报警
     */
    public void addActiveAlarm(String deviceId, String alarmId) {
        String key = ALARM_ACTIVE_KEY + deviceId;
        redisTemplate.opsForSet().add(key, alarmId);
        redisTemplate.expire(key, Duration.ofHours(24));
    }
    
    /**
     * 移除活跃报警
     */
    public void removeActiveAlarm(String deviceId, String alarmId) {
        String key = ALARM_ACTIVE_KEY + deviceId;
        redisTemplate.opsForSet().remove(key, alarmId);
    }
    
    /**
     * 获取设备的所有活跃报警
     */
    public Set<Object> getActiveAlarms(String deviceId) {
        String key = ALARM_ACTIVE_KEY + deviceId;
        return redisTemplate.opsForSet().members(key);
    }
    
    /**
     * 检查是否有活跃报警
     */
    public boolean hasActiveAlarms(String deviceId) {
        String key = ALARM_ACTIVE_KEY + deviceId;
        Long size = redisTemplate.opsForSet().size(key);
        return size != null && size > 0;
    }
    
    /**
     * 添加报警历史
     */
    public void addAlarmHistory(String deviceId, String alarmId) {
        String key = ALARM_HISTORY_KEY + deviceId;
        redisTemplate.opsForList().rightPush(key, alarmId);
        // 只保留最近100条历史记录
        Long size = redisTemplate.opsForList().size(key);
        if (size != null && size > 100) {
            redisTemplate.opsForList().trim(key, size - 100, -1);
        }
        redisTemplate.expire(key, Duration.ofDays(7));
    }
    
    /**
     * 获取报警历史
     */
    public List<Object> getAlarmHistory(String deviceId, int start, int end) {
        String key = ALARM_HISTORY_KEY + deviceId;
        return redisTemplate.opsForList().range(key, start, end);
    }
    
    // ==================== 用户会话管理 ====================
    
    private static final String USER_SESSION_KEY = "user:session:";
    private static final String USER_TOKEN_KEY = "user:token:";
    
    /**
     * 存储用户会话
     */
    public void storeUserSession(String sessionId, Map<String, Object> sessionData) {
        String key = USER_SESSION_KEY + sessionId;
        redisTemplate.opsForHash().putAll(key, sessionData);
        redisTemplate.expire(key, Duration.ofHours(2));
    }
    
    /**
     * 获取用户会话
     */
    public Map<Object, Object> getUserSession(String sessionId) {
        String key = USER_SESSION_KEY + sessionId;
        return redisTemplate.opsForHash().entries(key);
    }
    
    /**
     * 删除用户会话
     */
    public void deleteUserSession(String sessionId) {
        String key = USER_SESSION_KEY + sessionId;
        redisTemplate.delete(key);
    }
    
    /**
     * 存储用户令牌
     */
    public void storeUserToken(String userId, String token, Map<String, Object> tokenData) {
        String key = USER_TOKEN_KEY + userId + ":" + token;
        redisTemplate.opsForHash().putAll(key, tokenData);
        redisTemplate.expire(key, Duration.ofDays(7));
    }
    
    /**
     * 验证用户令牌
     */
    public boolean validateUserToken(String userId, String token) {
        String key = USER_TOKEN_KEY + userId + ":" + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    
    /**
     * 删除用户令牌
     */
    public void deleteUserToken(String userId, String token) {
        String key = USER_TOKEN_KEY + userId + ":" + token;
        redisTemplate.delete(key);
    }
    
    // ==================== 系统配置缓存 ====================
    
    private static final String CONFIG_CACHE_KEY = "config:";
    
    /**
     * 缓存系统配置
     */
    public void cacheSystemConfig(String configKey, Object configValue) {
        String key = CONFIG_CACHE_KEY + configKey;
        redisTemplate.opsForValue().set(key, configValue, Duration.ofHours(24));
    }
    
    /**
     * 获取缓存的系统配置
     */
    public Object getCachedSystemConfig(String configKey) {
        String key = CONFIG_CACHE_KEY + configKey;
        return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 删除缓存的系统配置
     */
    public void deleteCachedSystemConfig(String configKey) {
        String key = CONFIG_CACHE_KEY + configKey;
        redisTemplate.delete(key);
    }
    
    // ==================== 实时监控数据 ====================
    
    private static final String MONITOR_REALTIME_KEY = "monitor:realtime:";
    private static final String MONITOR_METRICS_KEY = "monitor:metrics:";
    
    /**
     * 存储实时监控数据
     */
    public void storeRealtimeMonitorData(String metricName, double value) {
        String key = MONITOR_REALTIME_KEY + metricName;
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(30));
    }
    
    /**
     * 获取实时监控数据
     */
    public Double getRealtimeMonitorData(String metricName) {
        String key = MONITOR_REALTIME_KEY + metricName;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? Double.parseDouble(value.toString()) : null;
    }
    
    /**
     * 记录监控指标
     */
    public void recordMonitorMetric(String metricName, double value) {
        String key = MONITOR_METRICS_KEY + metricName;
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(key, System.currentTimeMillis() + ":" + value, System.currentTimeMillis());
        
        // 只保留最近1小时的数据
        long oneHourAgo = System.currentTimeMillis() - (60 * 60 * 1000);
        zSetOps.removeRangeByScore(key, 0, oneHourAgo);
    }
    
    /**
     * 获取监控指标历史
     */
    public Set<ZSetOperations.TypedTuple<Object>> getMonitorMetricHistory(String metricName, 
                                                                         long startTime, 
                                                                         long endTime) {
        String key = MONITOR_METRICS_KEY + metricName;
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, startTime, endTime);
    }
    
    // ==================== 消息队列功能 ====================
    
    /**
     * 发送消息到队列
     */
    public void sendToQueue(String queueName, Object message) {
        redisTemplate.opsForList().rightPush(queueName, message);
    }
    
    /**
     * 从队列接收消息
     */
    public Object receiveFromQueue(String queueName, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(queueName, timeout, unit);
    }
    
    /**
     * 获取队列长度
     */
    public Long getQueueLength(String queueName) {
        return redisTemplate.opsForList().size(queueName);
    }
    
    // ==================== 分布式锁功能 ====================
    
    /**
     * 尝试获取分布式锁
     */
    public Boolean tryLock(String lockKey, String lockValue, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, timeout, unit);
    }
    
    /**
     * 释放分布式锁
     */
    public void releaseLock(String lockKey, String lockValue) {
        // 使用Lua脚本确保原子性：只有锁的持有者才能释放锁
        String luaScript = 
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "    return redis.call('del', KEYS[1]) " +
            "else " +
            "    return 0 " +
            "end";
        
        redisTemplate.execute(new DefaultRedisScript<>(luaScript, Long.class), 
                             Collections.singletonList(lockKey), lockValue);
    }
    
    /**
     * 延长锁的过期时间
     */
    public Boolean renewLock(String lockKey, String lockValue, long timeout, TimeUnit unit) {
        String luaScript = 
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "    return redis.call('expire', KEYS[1], ARGV[2]) " +
            "else " +
            "    return 0 " +
            "end";
        
        Long result = redisTemplate.execute(
            new DefaultRedisScript<>(luaScript, Long.class),
            Collections.singletonList(lockKey),
            lockValue,
            unit.toSeconds(timeout)
        );
        
        return result != null && result == 1;
    }
}