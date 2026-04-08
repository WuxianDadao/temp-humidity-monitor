package com.iot.temphumidity.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * TDengine时序数据库操作Repository
 * 注意：TDengine使用原生SQL操作，不支持JPA
 */
@Repository
public class TDengineRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public TDengineRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 创建数据库（如果不存在）
     */
    public void createDatabaseIfNotExists(String databaseName) {
        String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        jdbcTemplate.execute(sql);
    }
    
    /**
     * 使用特定数据库
     */
    public void useDatabase(String databaseName) {
        String sql = "USE " + databaseName;
        jdbcTemplate.execute(sql);
    }
    
    /**
     * 创建传感器数据超级表
     */
    public void createSensorDataSuperTable() {
        String sql = "CREATE STABLE IF NOT EXISTS sensor_data (" +
                     "ts TIMESTAMP, " +
                     "device_id NCHAR(50), " +
                     "sensor_id NCHAR(50), " +
                     "temperature DOUBLE, " +
                     "humidity DOUBLE, " +
                     "battery_voltage DOUBLE, " +
                     "signal_strength INT, " +
                     "location NCHAR(100), " +
                     "tags NCHAR(200)" +
                     ") TAGS (gateway_id NCHAR(50), tenant_id NCHAR(50))";
        jdbcTemplate.execute(sql);
    }
    
    /**
     * 创建设备状态超级表
     */
    public void createDeviceStatusSuperTable() {
        String sql = "CREATE STABLE IF NOT EXISTS device_status (" +
                     "ts TIMESTAMP, " +
                     "device_id NCHAR(50), " +
                     "online_status BOOL, " +
                     "last_heartbeat TIMESTAMP, " +
                     "cpu_usage DOUBLE, " +
                     "memory_usage DOUBLE, " +
                     "disk_usage DOUBLE, " +
                     "network_rx BIGINT, " +
                     "network_tx BIGINT, " +
                     "error_count INT" +
                     ") TAGS (gateway_id NCHAR(50), tenant_id NCHAR(50))";
        jdbcTemplate.execute(sql);
    }
    
    /**
     * 创建报警事件超级表
     */
    public void createAlarmEventSuperTable() {
        String sql = "CREATE STABLE IF NOT EXISTS alarm_events (" +
                     "ts TIMESTAMP, " +
                     "alarm_id NCHAR(50), " +
                     "device_id NCHAR(50), " +
                     "sensor_id NCHAR(50), " +
                     "alarm_type NCHAR(50), " +
                     "severity NCHAR(20), " +
                     "value DOUBLE, " +
                     "threshold DOUBLE, " +
                     "description NCHAR(200), " +
                     "status NCHAR(20)" +
                     ") TAGS (gateway_id NCHAR(50), tenant_id NCHAR(50), rule_id NCHAR(50))";
        jdbcTemplate.execute(sql);
    }
    
    /**
     * 插入传感器数据
     */
    public int insertSensorData(String tableName, String deviceId, String sensorId, 
                               double temperature, double humidity, 
                               double batteryVoltage, int signalStrength,
                               String location, String tags, 
                               String gatewayId, String tenantId) {
        String sql = "INSERT INTO ? USING sensor_data TAGS (?, ?) VALUES (NOW, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, tableName, gatewayId, tenantId, 
                                  deviceId, sensorId, temperature, humidity, 
                                  batteryVoltage, signalStrength, location, tags);
    }
    
    /**
     * 批量插入传感器数据
     */
    public int batchInsertSensorData(List<Object[]> dataList) {
        String sql = "INSERT INTO ? USING sensor_data TAGS (?, ?) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // 使用批量更新
        return jdbcTemplate.batchUpdate(sql, dataList).length;
    }
    
    /**
     * 查询传感器最新数据
     */
    public List<Map<String, Object>> findLatestSensorData(String deviceId, String sensorId, int limit) {
        String sql = "SELECT LAST(*) FROM sensor_data WHERE device_id = ? AND sensor_id = ? LIMIT ?";
        return jdbcTemplate.queryForList(sql, deviceId, sensorId, limit);
    }
    
    /**
     * 查询传感器历史数据
     */
    public List<Map<String, Object>> findSensorDataByTimeRange(String deviceId, String sensorId, 
                                                              LocalDateTime startTime, 
                                                              LocalDateTime endTime) {
        String sql = "SELECT * FROM sensor_data WHERE device_id = ? AND sensor_id = ? " +
                    "AND ts >= ? AND ts <= ? ORDER BY ts DESC";
        return jdbcTemplate.queryForList(sql, deviceId, sensorId, 
                                        Timestamp.valueOf(startTime), 
                                        Timestamp.valueOf(endTime));
    }
    
    /**
     * 查询设备的最新状态
     */
    public Map<String, Object> findLatestDeviceStatus(String deviceId) {
        String sql = "SELECT LAST(*) FROM device_status WHERE device_id = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, deviceId);
        return result.isEmpty() ? null : result.get(0);
    }
    
    /**
     * 更新设备状态
     */
    public int updateDeviceStatus(String deviceId, boolean online, String gatewayId, String tenantId) {
        String sql = "INSERT INTO ? USING device_status TAGS (?, ?) " +
                    "VALUES (NOW, ?, ?, NOW, 0.0, 0.0, 0.0, 0, 0, 0)";
        return jdbcTemplate.update(sql, "d_" + deviceId, gatewayId, tenantId, deviceId, online);
    }
    
    /**
     * 查询温度超过阈值的数据
     */
    public List<Map<String, Object>> findHighTemperatureData(double threshold, 
                                                           LocalDateTime startTime, 
                                                           LocalDateTime endTime) {
        String sql = "SELECT * FROM sensor_data WHERE temperature > ? " +
                    "AND ts >= ? AND ts <= ? ORDER BY temperature DESC";
        return jdbcTemplate.queryForList(sql, threshold, 
                                        Timestamp.valueOf(startTime), 
                                        Timestamp.valueOf(endTime));
    }
    
    /**
     * 查询湿度超过阈值的数据
     */
    public List<Map<String, Object>> findHighHumidityData(double threshold, 
                                                        LocalDateTime startTime, 
                                                        LocalDateTime endTime) {
        String sql = "SELECT * FROM sensor_data WHERE humidity > ? " +
                    "AND ts >= ? AND ts <= ? ORDER BY humidity DESC";
        return jdbcTemplate.queryForList(sql, threshold, 
                                        Timestamp.valueOf(startTime), 
                                        Timestamp.valueOf(endTime));
    }
    
    /**
     * 统计传感器数据量
     */
    public long countSensorData(String deviceId, String sensorId, 
                               LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT COUNT(*) FROM sensor_data WHERE device_id = ? AND sensor_id = ? " +
                    "AND ts >= ? AND ts <= ?";
        return jdbcTemplate.queryForObject(sql, Long.class, 
                                          deviceId, sensorId, 
                                          Timestamp.valueOf(startTime), 
                                          Timestamp.valueOf(endTime));
    }
    
    /**
     * 计算传感器数据平均值
     */
    public Map<String, Object> calculateSensorStatistics(String deviceId, String sensorId, 
                                                        LocalDateTime startTime, 
                                                        LocalDateTime endTime) {
        String sql = "SELECT AVG(temperature) as avg_temp, " +
                    "MIN(temperature) as min_temp, " +
                    "MAX(temperature) as max_temp, " +
                    "AVG(humidity) as avg_hum, " +
                    "MIN(humidity) as min_hum, " +
                    "MAX(humidity) as max_hum " +
                    "FROM sensor_data WHERE device_id = ? AND sensor_id = ? " +
                    "AND ts >= ? AND ts <= ?";
        return jdbcTemplate.queryForMap(sql, deviceId, sensorId, 
                                       Timestamp.valueOf(startTime), 
                                       Timestamp.valueOf(endTime));
    }
    
    /**
     * 按时间窗口聚合数据（降采样）
     */
    public List<Map<String, Object>> aggregateSensorDataByInterval(String deviceId, String sensorId, 
                                                                  LocalDateTime startTime, 
                                                                  LocalDateTime endTime, 
                                                                  String interval) {
        String sql = "SELECT _WSTART as window_start, _WEND as window_end, " +
                    "AVG(temperature) as avg_temp, AVG(humidity) as avg_hum " +
                    "FROM sensor_data WHERE device_id = ? AND sensor_id = ? " +
                    "AND ts >= ? AND ts <= ? " +
                    "INTERVAL(?)";
        return jdbcTemplate.queryForList(sql, deviceId, sensorId, 
                                        Timestamp.valueOf(startTime), 
                                        Timestamp.valueOf(endTime), interval);
    }
    
    /**
     * 查询设备数据趋势
     */
    public List<Map<String, Object>> findDeviceDataTrend(String deviceId, 
                                                        LocalDateTime startTime, 
                                                        LocalDateTime endTime, 
                                                        String metric) {
        String sql = "SELECT _WSTART as time, AVG(" + metric + ") as value " +
                    "FROM sensor_data WHERE device_id = ? " +
                    "AND ts >= ? AND ts <= ? " +
                    "INTERVAL(1h)";
        return jdbcTemplate.queryForList(sql, deviceId, 
                                        Timestamp.valueOf(startTime), 
                                        Timestamp.valueOf(endTime));
    }
    
    /**
     * 查询报警事件
     */
    public List<Map<String, Object>> findAlarmEvents(String deviceId, String alarmType, 
                                                    LocalDateTime startTime, 
                                                    LocalDateTime endTime) {
        String sql = "SELECT * FROM alarm_events WHERE device_id = ? " +
                    "AND alarm_type = ? AND ts >= ? AND ts <= ? ORDER BY ts DESC";
        return jdbcTemplate.queryForList(sql, deviceId, alarmType, 
                                        Timestamp.valueOf(startTime), 
                                        Timestamp.valueOf(endTime));
    }
    
    /**
     * 插入报警事件
     */
    public int insertAlarmEvent(String alarmId, String deviceId, String sensorId, 
                               String alarmType, String severity, 
                               double value, double threshold, 
                               String description, String status, 
                               String gatewayId, String tenantId, String ruleId) {
        String sql = "INSERT INTO ? USING alarm_events TAGS (?, ?, ?) " +
                    "VALUES (NOW, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, "a_" + alarmId, gatewayId, tenantId, ruleId,
                                  alarmId, deviceId, sensorId, alarmType, severity, 
                                  value, threshold, description, status);
    }
    
    /**
     * 清理过期数据（数据保留策略）
     */
    public int cleanupExpiredData(LocalDateTime expireTime) {
        String sql = "DELETE FROM sensor_data WHERE ts < ?";
        return jdbcTemplate.update(sql, Timestamp.valueOf(expireTime));
    }
    
    /**
     * 获取数据库信息
     */
    public List<Map<String, Object>> getDatabaseInfo() {
        String sql = "SHOW DATABASES";
        return jdbcTemplate.queryForList(sql);
    }
    
    /**
     * 获取表信息
     */
    public List<Map<String, Object>> getTableInfo(String databaseName) {
        String sql = "SHOW " + databaseName + ".TABLES";
        return jdbcTemplate.queryForList(sql);
    }
    
    /**
     * 检查表是否存在
     */
    public boolean tableExists(String tableName) {
        try {
            String sql = "DESCRIBE " + tableName;
            jdbcTemplate.queryForList(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 执行自定义SQL查询
     */
    public List<Map<String, Object>> executeQuery(String sql, Object... params) {
        return jdbcTemplate.queryForList(sql, params);
    }
    
    /**
     * 执行自定义SQL更新
     */
    public int executeUpdate(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }
}