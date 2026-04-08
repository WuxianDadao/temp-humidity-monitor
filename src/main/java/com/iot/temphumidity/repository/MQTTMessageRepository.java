package com.iot.temphumidity.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * MQTT消息处理Repository
 * 用于管理MQTT消息的接收、处理和存储
 */
@Repository
public class MQTTMessageRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(MQTTMessageRepository.class);
    
    private final ObjectMapper objectMapper;
    
    // 消息缓存：用于存储最近接收的消息
    private final Map<String, List<Map<String, Object>>> messageCache = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastMessageTime = new ConcurrentHashMap<>();
    
    // 设备主题订阅关系
    private final Map<String, Set<String>> deviceSubscriptions = new ConcurrentHashMap<>();
    
    // 消息处理统计
    private final Map<String, MessageStats> messageStats = new ConcurrentHashMap<>();
    
    public MQTTMessageRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    // ==================== 消息接收和处理 ====================
    
    /**
     * 处理接收到的MQTT消息
     */
    public void processMessage(String topic, String payload) {
        try {
            // 解析消息
            Map<String, Object> message = parseMessage(payload);
            
            // 提取设备ID
            String deviceId = extractDeviceId(topic, message);
            
            // 更新统计信息
            updateMessageStats(deviceId, topic);
            
            // 缓存消息
            cacheMessage(deviceId, topic, message);
            
            // 更新最后消息时间
            lastMessageTime.put(deviceId, LocalDateTime.now());
            
            // 记录日志
            logger.info("Processed MQTT message from device {} on topic {}", deviceId, topic);
            
        } catch (Exception e) {
            logger.error("Failed to process MQTT message on topic {}: {}", topic, e.getMessage(), e);
        }
    }
    
    /**
     * 解析MQTT消息负载
     */
    private Map<String, Object> parseMessage(String payload) {
        try {
            return objectMapper.readValue(payload, Map.class);
        } catch (Exception e) {
            // 如果JSON解析失败，尝试其他格式
            Map<String, Object> message = new HashMap<>();
            message.put("raw_payload", payload);
            message.put("timestamp", LocalDateTime.now().toString());
            return message;
        }
    }
    
    /**
     * 从主题或消息中提取设备ID
     */
    private String extractDeviceId(String topic, Map<String, Object> message) {
        // 尝试从主题中提取设备ID
        String[] topicParts = topic.split("/");
        if (topicParts.length >= 2) {
            return topicParts[1]; // 假设主题格式为: iot/deviceId/...
        }
        
        // 尝试从消息中提取设备ID
        if (message.containsKey("device_id")) {
            return message.get("device_id").toString();
        }
        if (message.containsKey("deviceId")) {
            return message.get("deviceId").toString();
        }
        if (message.containsKey("gateway_id")) {
            return message.get("gateway_id").toString();
        }
        
        // 如果都提取不到，使用主题作为设备ID
        return topic;
    }
    
    /**
     * 缓存消息
     */
    private void cacheMessage(String deviceId, String topic, Map<String, Object> message) {
        String cacheKey = deviceId + ":" + topic;
        
        messageCache.computeIfAbsent(cacheKey, k -> new CopyOnWriteArrayList<>())
                   .add(message);
        
        // 限制缓存大小，只保留最近100条消息
        List<Map<String, Object>> messages = messageCache.get(cacheKey);
        if (messages.size() > 100) {
            messages = messages.subList(messages.size() - 100, messages.size());
            messageCache.put(cacheKey, new CopyOnWriteArrayList<>(messages));
        }
    }
    
    // ==================== 消息查询和检索 ====================
    
    /**
     * 获取设备的最新消息
     */
    public List<Map<String, Object>> getLatestMessages(String deviceId, String topic, int limit) {
        String cacheKey = deviceId + ":" + topic;
        List<Map<String, Object>> messages = messageCache.get(cacheKey);
        
        if (messages == null || messages.isEmpty()) {
            return new ArrayList<>();
        }
        
        int start = Math.max(0, messages.size() - limit);
        return new ArrayList<>(messages.subList(start, messages.size()));
    }
    
    /**
     * 获取设备的所有消息主题
     */
    public Set<String> getDeviceTopics(String deviceId) {
        Set<String> topics = new HashSet<>();
        
        for (String cacheKey : messageCache.keySet()) {
            if (cacheKey.startsWith(deviceId + ":")) {
                String topic = cacheKey.substring(deviceId.length() + 1);
                topics.add(topic);
            }
        }
        
        return topics;
    }
    
    /**
     * 获取设备最后消息时间
     */
    public LocalDateTime getLastMessageTime(String deviceId) {
        return lastMessageTime.get(deviceId);
    }
    
    /**
     * 检查设备是否活跃（最近有消息）
     */
    public boolean isDeviceActive(String deviceId, int timeoutMinutes) {
        LocalDateTime lastTime = lastMessageTime.get(deviceId);
        if (lastTime == null) {
            return false;
        }
        
        LocalDateTime timeoutThreshold = LocalDateTime.now().minusMinutes(timeoutMinutes);
        return lastTime.isAfter(timeoutThreshold);
    }
    
    /**
     * 获取所有活跃设备
     */
    public Set<String> getActiveDevices(int timeoutMinutes) {
        Set<String> activeDevices = new HashSet<>();
        LocalDateTime timeoutThreshold = LocalDateTime.now().minusMinutes(timeoutMinutes);
        
        for (Map.Entry<String, LocalDateTime> entry : lastMessageTime.entrySet()) {
            if (entry.getValue().isAfter(timeoutThreshold)) {
                activeDevices.add(entry.getKey());
            }
        }
        
        return activeDevices;
    }
    
    // ==================== 主题订阅管理 ====================
    
    /**
     * 订阅设备主题
     */
    public void subscribeToTopic(String deviceId, String topic) {
        deviceSubscriptions.computeIfAbsent(deviceId, k -> ConcurrentHashMap.newKeySet())
                          .add(topic);
        logger.info("Device {} subscribed to topic {}", deviceId, topic);
    }
    
    /**
     * 取消订阅设备主题
     */
    public void unsubscribeFromTopic(String deviceId, String topic) {
        Set<String> subscriptions = deviceSubscriptions.get(deviceId);
        if (subscriptions != null) {
            subscriptions.remove(topic);
            logger.info("Device {} unsubscribed from topic {}", deviceId, topic);
        }
    }
    
    /**
     * 获取设备的订阅主题
     */
    public Set<String> getDeviceSubscriptions(String deviceId) {
        return deviceSubscriptions.getOrDefault(deviceId, Collections.emptySet());
    }
    
    /**
     * 检查设备是否订阅了主题
     */
    public boolean isSubscribed(String deviceId, String topic) {
        Set<String> subscriptions = deviceSubscriptions.get(deviceId);
        return subscriptions != null && subscriptions.contains(topic);
    }
    
    // ==================== 消息统计和分析 ====================
    
    /**
     * 更新消息统计信息
     */
    private void updateMessageStats(String deviceId, String topic) {
        String statsKey = deviceId + ":" + topic;
        
        MessageStats stats = messageStats.computeIfAbsent(statsKey, k -> new MessageStats());
        stats.incrementMessageCount();
        stats.setLastMessageTime(LocalDateTime.now());
        
        // 更新设备级别的统计
        String deviceStatsKey = deviceId;
        MessageStats deviceStats = messageStats.computeIfAbsent(deviceStatsKey, k -> new MessageStats());
        deviceStats.incrementMessageCount();
        deviceStats.setLastMessageTime(LocalDateTime.now());
    }
    
    /**
     * 获取消息统计信息
     */
    public MessageStats getMessageStats(String deviceId, String topic) {
        String statsKey = deviceId + ":" + topic;
        return messageStats.get(statsKey);
    }
    
    /**
     * 获取设备消息统计信息
     */
    public MessageStats getDeviceMessageStats(String deviceId) {
        return messageStats.get(deviceId);
    }
    
    /**
     * 获取所有设备的统计信息
     */
    public Map<String, MessageStats> getAllMessageStats() {
        return new HashMap<>(messageStats);
    }
    
    /**
     * 获取消息频率（每分钟消息数）
     */
    public double getMessageFrequency(String deviceId, String topic, int minutes) {
        MessageStats stats = getMessageStats(deviceId, topic);
        if (stats == null) {
            return 0.0;
        }
        
        // 这里简化计算，实际应该基于时间窗口计算
        return stats.getMessageCount() / (double) minutes;
    }
    
    // ==================== 消息过滤和转换 ====================
    
    /**
     * 过滤消息（根据条件）
     */
    public List<Map<String, Object>> filterMessages(String deviceId, String topic, 
                                                   Map<String, Object> filterCriteria) {
        List<Map<String, Object>> messages = getLatestMessages(deviceId, topic, 1000);
        List<Map<String, Object>> filteredMessages = new ArrayList<>();
        
        for (Map<String, Object> message : messages) {
            if (matchesFilter(message, filterCriteria)) {
                filteredMessages.add(message);
            }
        }
        
        return filteredMessages;
    }
    
    /**
     * 检查消息是否匹配过滤条件
     */
    private boolean matchesFilter(Map<String, Object> message, Map<String, Object> filterCriteria) {
        for (Map.Entry<String, Object> filterEntry : filterCriteria.entrySet()) {
            String key = filterEntry.getKey();
            Object expectedValue = filterEntry.getValue();
            
            if (!message.containsKey(key)) {
                return false;
            }
            
            Object actualValue = message.get(key);
            if (!Objects.equals(actualValue, expectedValue)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 转换消息格式
     */
    public Map<String, Object> transformMessage(Map<String, Object> originalMessage, 
                                               Map<String, String> fieldMappings) {
        Map<String, Object> transformed = new HashMap<>();
        
        for (Map.Entry<String, String> mapping : fieldMappings.entrySet()) {
            String sourceField = mapping.getKey();
            String targetField = mapping.getValue();
            
            if (originalMessage.containsKey(sourceField)) {
                transformed.put(targetField, originalMessage.get(sourceField));
            }
        }
        
        // 添加转换时间戳
        transformed.put("transformed_at", LocalDateTime.now().toString());
        
        return transformed;
    }
    
    // ==================== 消息持久化 ====================
    
    /**
     * 清理过期的缓存消息
     */
    public void cleanupExpiredMessages(int maxAgeHours) {
        LocalDateTime expireTime = LocalDateTime.now().minusHours(maxAgeHours);
        
        // 清理消息缓存
        for (Map.Entry<String, List<Map<String, Object>>> entry : messageCache.entrySet()) {
            List<Map<String, Object>> messages = entry.getValue();
            messages.removeIf(message -> {
                String timestamp = (String) message.getOrDefault("timestamp", "");
                if (timestamp.isEmpty()) {
                    return true; // 没有时间戳的消息也清理掉
                }
                
                try {
                    LocalDateTime messageTime = LocalDateTime.parse(timestamp);
                    return messageTime.isBefore(expireTime);
                } catch (Exception e) {
                    return true; // 解析失败的消息清理掉
                }
            });
        }
        
        // 清理过期的最后消息时间记录
        lastMessageTime.entrySet().removeIf(entry -> entry.getValue().isBefore(expireTime));
        
        logger.info("Cleaned up expired MQTT messages (older than {} hours)", maxAgeHours);
    }
    
    /**
     * 导出消息到文件（用于调试和分析）
     */
    public String exportMessages(String deviceId, String topic, int limit) {
        List<Map<String, Object>> messages = getLatestMessages(deviceId, topic, limit);
        
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(messages);
        } catch (Exception e) {
            logger.error("Failed to export messages: {}", e.getMessage(), e);
            return "[]";
        }
    }
    
    // ==================== 内部统计类 ====================
    
    /**
     * 消息统计信息类
     */
    public static class MessageStats {
        private long messageCount;
        private LocalDateTime lastMessageTime;
        private LocalDateTime firstMessageTime;
        
        public MessageStats() {
            this.messageCount = 0;
            this.firstMessageTime = LocalDateTime.now();
        }
        
        public void incrementMessageCount() {
            this.messageCount++;
            this.lastMessageTime = LocalDateTime.now();
        }
        
        public long getMessageCount() {
            return messageCount;
        }
        
        public LocalDateTime getLastMessageTime() {
            return lastMessageTime;
        }
        
        public void setLastMessageTime(LocalDateTime lastMessageTime) {
            this.lastMessageTime = lastMessageTime;
        }
        
        public LocalDateTime getFirstMessageTime() {
            return firstMessageTime;
        }
        
        public long getMessagesPerMinute() {
            if (firstMessageTime == null || lastMessageTime == null) {
                return 0;
            }
            
            long minutes = java.time.Duration.between(firstMessageTime, lastMessageTime).toMinutes();
            if (minutes == 0) {
                return messageCount;
            }
            
            return messageCount / minutes;
        }
        
        @Override
        public String toString() {
            return String.format("MessageStats{count=%d, last=%s, first=%s}", 
                               messageCount, lastMessageTime, firstMessageTime);
        }
    }
}