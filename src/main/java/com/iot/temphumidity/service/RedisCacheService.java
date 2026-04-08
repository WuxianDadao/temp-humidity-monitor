package com.iot.temphumidity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存服务
 */
@Service
@Slf4j
public class RedisCacheService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;
    
    /**
     * 设置缓存值
     */
    public void set(String key, Object value) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(key, value);
            log.debug("设置缓存成功 - Key: {}", key);
        } catch (Exception e) {
            log.error("设置缓存失败 - Key: {}, Error: {}", key, e.getMessage());
        }
    }
    
    /**
     * 设置缓存值并指定过期时间
     */
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(key, value, timeout, unit);
            log.debug("设置缓存成功(带过期) - Key: {}, Timeout: {} {}", key, timeout, unit);
        } catch (Exception e) {
            log.error("设置缓存失败(带过期) - Key: {}, Error: {}", key, e.getMessage());
        }
    }
    
    /**
     * 获取缓存值
     */
    public Object get(String key) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            return ops.get(key);
        } catch (Exception e) {
            log.error("获取缓存失败 - Key: {}, Error: {}", key, e.getMessage());
            return null;
        }
    }
    
    /**
     * 获取缓存值（指定类型）
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        try {
            Object value = get(key);
            if (value != null && type.isInstance(value)) {
                return (T) value;
            }
            return null;
        } catch (Exception e) {
            log.error("获取缓存失败(类型转换) - Key: {}, Type: {}, Error: {}", key, type, e.getMessage());
            return null;
        }
    }
    
    /**
     * 删除缓存
     */
    public boolean delete(String key) {
        try {
            Boolean result = redisTemplate.delete(key);
            if (Boolean.TRUE.equals(result)) {
                log.debug("删除缓存成功 - Key: {}", key);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("删除缓存失败 - Key: {}, Error: {}", key, e.getMessage());
            return false;
        }
    }
    
    /**
     * 检查缓存是否存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean result = redisTemplate.hasKey(key);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("检查缓存是否存在失败 - Key: {}, Error: {}", key, e.getMessage());
            return false;
        }
    }
    
    /**
     * 设置过期时间
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            Boolean result = redisTemplate.expire(key, timeout, unit);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("设置缓存过期时间失败 - Key: {}, Error: {}", key, e.getMessage());
            return false;
        }
    }
    
    /**
     * 获取剩余过期时间
     */
    public long getExpire(String key, TimeUnit unit) {
        try {
            Long time = redisTemplate.getExpire(key, unit);
            return time != null ? time : -2; // -2表示key不存在, -1表示没有过期时间
        } catch (Exception e) {
            log.error("获取缓存过期时间失败 - Key: {}, Error: {}", key, e.getMessage());
            return -3; // 表示获取失败
        }
    }
    
    /**
     * 缓存字符串值
     */
    public void setString(String key, String value) {
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(key, value);
        } catch (Exception e) {
            log.error("设置字符串缓存失败 - Key: {}, Error: {}", key, e.getMessage());
        }
    }
    
    /**
     * 获取字符串缓存值
     */
    public String getString(String key) {
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            return ops.get(key);
        } catch (Exception e) {
            log.error("获取字符串缓存失败 - Key: {}, Error: {}", key, e.getMessage());
            return null;
        }
    }
    
    /**
     * 缓存网关在线状态
     */
    public void setGatewayOnline(String gatewayId) {
        String key = "gateway:online:" + gatewayId;
        setWithExpire(key, "online", 5, TimeUnit.MINUTES); // 5分钟过期
    }
    
    /**
     * 检查网关是否在线
     */
    public boolean isGatewayOnline(String gatewayId) {
        String key = "gateway:online:" + gatewayId;
        return hasKey(key);
    }
    
    /**
     * 缓存传感器最新数据
     */
    public void cacheSensorLatestData(String sensorId, Object data) {
        String key = "sensor:latest:" + sensorId;
        setWithExpire(key, data, 2, TimeUnit.MINUTES); // 2分钟过期
    }
    
    /**
     * 获取传感器最新数据
     */
    public Object getSensorLatestData(String sensorId) {
        String key = "sensor:latest:" + sensorId;
        return get(key);
    }
    
    /**
     * 缓存用户权限信息
     */
    public void cacheUserPermissions(Long userId, Object permissions) {
        String key = "user:permissions:" + userId;
        setWithExpire(key, permissions, 30, TimeUnit.MINUTES); // 30分钟过期
    }
    
    /**
     * 获取用户权限信息
     */
    public Object getUserPermissions(Long userId) {
        String key = "user:permissions:" + userId;
        return get(key);
    }
    
    /**
     * 清理用户相关缓存
     */
    public void clearUserCache(Long userId) {
        String[] patterns = {
            "user:permissions:" + userId,
            "user:gateways:" + userId,
            "user:sensors:" + userId
        };
        
        for (String pattern : patterns) {
            delete(pattern);
        }
    }
    
    /**
     * 测试Redis连接
     */
    public String testConnection() {
        try {
            String testKey = "test:connection";
            String testValue = "redis-test-" + System.currentTimeMillis();
            
            setString(testKey, testValue);
            String retrievedValue = getString(testKey);
            
            if (testValue.equals(retrievedValue)) {
                delete(testKey);
                return "✅ Redis连接测试成功！";
            } else {
                return "⚠️ Redis连接测试：数据不匹配";
            }
        } catch (Exception e) {
            return "❌ Redis连接测试失败：" + e.getMessage();
        }
    }
}