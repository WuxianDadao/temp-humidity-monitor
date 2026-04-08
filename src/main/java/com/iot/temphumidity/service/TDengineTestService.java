package com.iot.temphumidity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TDengineTestService {

    @Autowired
    private JdbcTemplate tdengineJdbcTemplate;

    /**
     * 测试TDengine连接
     */
    public boolean testConnection() {
        try {
            // 执行一个简单的查询
            List<Map<String, Object>> result = tdengineJdbcTemplate.queryForList("SELECT 1");
            log.info("✅ TDengine连接测试成功: {}", result);
            return true;
        } catch (Exception e) {
            log.error("❌ TDengine连接测试失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取TDengine版本信息
     */
    public String getTDengineVersion() {
        try {
            List<Map<String, Object>> result = tdengineJdbcTemplate.queryForList("SELECT CLIENT_VERSION() as version");
            if (!result.isEmpty()) {
                String version = result.get(0).get("version").toString();
                log.info("📊 TDengine客户端版本: {}", version);
                return version;
            }
        } catch (Exception e) {
            log.error("❌ 获取TDengine版本失败: {}", e.getMessage());
        }
        return "未知";
    }

    /**
     * 获取数据库信息
     */
    public void getDatabaseInfo() {
        try {
            // 切换到iot_db数据库
            tdengineJdbcTemplate.execute("USE iot_db");
            
            // 查询所有超表
            List<Map<String, Object>> stables = tdengineJdbcTemplate.queryForList(
                "SHOW STABLES"
            );
            
            log.info("📊 数据库超表列表:");
            for (Map<String, Object> stable : stables) {
                log.info("  超表: {}", stable.get("stable_name"));
            }
            
            if (stables.isEmpty()) {
                log.warn("⚠️ 数据库中没有找到超表");
            }
            
        } catch (Exception e) {
            log.error("❌ 获取数据库信息失败: {}", e.getMessage());
        }
    }

    /**
     * 创建测试数据
     */
    public void createTestData() {
        try {
            tdengineJdbcTemplate.execute("USE iot_db");
            
            // 创建子表
            String createSubTableSQL = """
                CREATE TABLE IF NOT EXISTS device_001 USING sensor_data TAGS (
                    'device_001',
                    '办公室',
                    'DHT22'
                )
                """;
            
            tdengineJdbcTemplate.execute(createSubTableSQL);
            log.info("✅ 创建测试子表: device_001");
            
            // 插入测试数据
            String insertDataSQL = """
                INSERT INTO device_001 VALUES
                (NOW - 10m, 25.3, 65.2, 3.3, -45),
                (NOW - 5m, 25.5, 64.8, 3.2, -46),
                (NOW, 25.7, 65.0, 3.3, -44)
                """;
            
            tdengineJdbcTemplate.execute(insertDataSQL);
            log.info("✅ 插入测试数据成功");
            
            // 查询数据
            List<Map<String, Object>> data = tdengineJdbcTemplate.queryForList(
                "SELECT * FROM device_001 ORDER BY ts DESC LIMIT 5"
            );
            
            log.info("📊 最新数据:");
            for (Map<String, Object> row : data) {
                log.info("  时间: {}, 温度: {}°C, 湿度: {}%", 
                    row.get("ts"), row.get("temperature"), row.get("humidity"));
            }
            
        } catch (Exception e) {
            log.error("❌ 创建测试数据失败: {}", e.getMessage());
        }
    }

    /**
     * 完整的连接测试流程
     */
    public boolean fullConnectionTest() {
        log.info("🔍 开始TDengine完整连接测试...");
        
        boolean connectionTest = testConnection();
        if (!connectionTest) {
            log.error("❌ 连接测试失败，停止后续测试");
            return false;
        }
        
        getTDengineVersion();
        getDatabaseInfo();
        createTestData();
        
        log.info("🎉 TDengine完整连接测试完成!");
        return true;
    }
    
    /**
     * 获取连接信息
     */
    public String getConnectionInfo() {
        boolean isConnected = testConnection();
        String version = getTDengineVersion();
        
        return String.format("TDengine 连接状态: %s, 版本: %s", 
            isConnected ? "✅ 已连接" : "❌ 未连接",
            version
        );
    }
    
    /**
     * 健康检查
     */
    public String healthCheck() {
        try {
            boolean isConnected = testConnection();
            if (isConnected) {
                return "✅ 健康";
            } else {
                return "❌ 不健康";
            }
        } catch (Exception e) {
            log.error("健康检查失败: {}", e.getMessage());
            return "❌ 检查失败";
        }
    }
    
    /**
     * 查询测试数据
     */
    public String queryTestData() throws SQLException {
        try {
            tdengineJdbcTemplate.execute("USE iot_db");
            
            // 查询测试数据
            List<Map<String, Object>> data = tdengineJdbcTemplate.queryForList(
                "SELECT * FROM device_001 ORDER BY ts DESC LIMIT 10"
            );
            
            if (data.isEmpty()) {
                return "没有找到测试数据";
            }
            
            StringBuilder result = new StringBuilder();
            result.append("📊 测试数据查询结果 (共").append(data.size()).append("条记录):\n");
            
            for (int i = 0; i < data.size(); i++) {
                Map<String, Object> row = data.get(i);
                result.append(String.format("%d. 时间: %s, 温度: %s°C, 湿度: %s%%\n", 
                    i + 1,
                    row.get("ts"),
                    row.get("temperature"),
                    row.get("humidity")
                ));
            }
            
            log.info("✅ 查询测试数据成功，共 {} 条记录", data.size());
            return result.toString();
            
        } catch (Exception e) {
            log.error("❌ 查询测试数据失败: {}", e.getMessage());
            throw new SQLException("查询测试数据失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取TDengine统计信息
     */
    public String getStats() throws SQLException {
        try {
            // 获取数据库统计信息
            List<Map<String, Object>> dbInfo = tdengineJdbcTemplate.queryForList("SHOW DATABASES");
            int dbCount = dbInfo.size();
            
            // 获取当前数据库的表信息
            List<Map<String, Object>> tables = tdengineJdbcTemplate.queryForList("SHOW TABLES");
            int tableCount = tables.size();
            
            // 获取连接信息
            List<Map<String, Object>> connections = tdengineJdbcTemplate.queryForList("SHOW CONNECTIONS");
            int connectionCount = connections.size();
            
            StringBuilder stats = new StringBuilder();
            stats.append("📊 TDengine统计信息:\n");
            stats.append("  • 数据库数量: ").append(dbCount).append("\n");
            stats.append("  • 表数量: ").append(tableCount).append("\n");
            stats.append("  • 连接数: ").append(connectionCount).append("\n");
            
            // 如果有数据库，显示详细信息
            if (dbCount > 0) {
                stats.append("\n📋 数据库列表:\n");
                for (Map<String, Object> db : dbInfo) {
                    stats.append("  - ").append(db.get("name")).append("\n");
                }
            }
            
            return stats.toString();
        } catch (Exception e) {
            log.error("❌ 获取统计信息失败: {}", e.getMessage());
            throw new SQLException("获取统计信息失败: " + e.getMessage());
        }
    }
}