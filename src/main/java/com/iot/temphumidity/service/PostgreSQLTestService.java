package com.iot.temphumidity.service;

import com.iot.temphumidity.repository.postgresql.GatewayRepository;
import com.iot.temphumidity.repository.postgresql.UserRepository;
import com.iot.temphumidity.repository.postgresql.SensorTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * PostgreSQL测试服务
 */
@Service
@Slf4j
public class PostgreSQLTestService {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GatewayRepository gatewayRepository;
    
    @Autowired
    private SensorTagRepository sensorTagRepository;
    
    /**
     * 测试PostgreSQL连接
     */
    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            String dbName = connection.getCatalog();
            String dbVersion = connection.getMetaData().getDatabaseProductVersion();
            String dbUrl = connection.getMetaData().getURL();
            
            String result = String.format(
                "✅ PostgreSQL连接成功！\n" +
                "数据库: %s\n" +
                "版本: %s\n" +
                "URL: %s\n" +
                "连接状态: 正常",
                dbName, dbVersion, dbUrl
            );
            
            log.info("PostgreSQL连接测试通过: {}", dbUrl);
            return result;
            
        } catch (SQLException e) {
            String error = String.format(
                "❌ PostgreSQL连接失败！\n" +
                "错误: %s\n" +
                "消息: %s",
                e.getClass().getSimpleName(), e.getMessage()
            );
            log.error("PostgreSQL连接测试失败", e);
            return error;
        }
    }
    
    /**
     * 测试Repository连接
     */
    public String testRepositoryConnection() {
        try {
            // 测试UserRepository
            long userCount = userRepository.count();
            
            // 测试GatewayRepository
            long gatewayCount = gatewayRepository.count();
            
            // 测试SensorTagRepository
            long sensorCount = sensorTagRepository.count();
            
            String result = String.format(
                "✅ Repository连接测试成功！\n" +
                "用户表记录数: %d\n" +
                "网关表记录数: %d\n" +
                "传感器表记录数: %d\n" +
                "所有Repository连接正常",
                userCount, gatewayCount, sensorCount
            );
            
            log.info("Repository连接测试通过");
            return result;
            
        } catch (Exception e) {
            String error = String.format(
                "❌ Repository连接测试失败！\n" +
                "错误: %s\n" +
                "消息: %s",
                e.getClass().getSimpleName(), e.getMessage()
            );
            log.error("Repository连接测试失败", e);
            return error;
        }
    }
    
    /**
     * 测试数据库操作
     */
    public String testDatabaseOperations() {
        try {
            // 测试数据库结构
            boolean userTableExists = userRepository.count() >= 0;
            boolean gatewayTableExists = gatewayRepository.count() >= 0;
            boolean sensorTableExists = sensorTagRepository.count() >= 0;
            
            String result = String.format(
                "✅ 数据库操作测试成功！\n" +
                "用户表存在: %s\n" +
                "网关表存在: %s\n" +
                "传感器表存在: %s\n" +
                "所有表可正常访问",
                userTableExists ? "✅" : "❌",
                gatewayTableExists ? "✅" : "❌",
                sensorTableExists ? "✅" : "❌"
            );
            
            log.info("数据库操作测试通过");
            return result;
            
        } catch (Exception e) {
            String error = String.format(
                "❌ 数据库操作测试失败！\n" +
                "错误: %s\n" +
                "消息: %s",
                e.getClass().getSimpleName(), e.getMessage()
            );
            log.error("数据库操作测试失败", e);
            return error;
        }
    }
    
    /**
     * 综合测试
     */
    public String runAllTests() {
        StringBuilder result = new StringBuilder();
        result.append("📊 PostgreSQL综合测试报告\n");
        result.append("=".repeat(50)).append("\n\n");
        
        // 测试1: 连接测试
        result.append("1. 数据库连接测试:\n");
        result.append(testConnection()).append("\n\n");
        
        // 测试2: Repository连接测试
        result.append("2. Repository连接测试:\n");
        result.append(testRepositoryConnection()).append("\n\n");
        
        // 测试3: 数据库操作测试
        result.append("3. 数据库操作测试:\n");
        result.append(testDatabaseOperations()).append("\n\n");
        
        result.append("=".repeat(50)).append("\n");
        result.append("✅ PostgreSQL配置测试完成！");
        
        return result.toString();
    }
    
    /**
     * 获取数据库信息
     */
    public String getDatabaseInfo() {
        try (Connection connection = dataSource.getConnection()) {
            return String.format(
                "📊 PostgreSQL数据库信息\n" +
                "数据库名称: %s\n" +
                "数据库版本: %s\n" +
                "JDBC驱动: %s %s\n" +
                "URL: %s\n" +
                "用户名: %s\n" +
                "事务隔离级别: %s",
                connection.getCatalog(),
                connection.getMetaData().getDatabaseProductVersion(),
                connection.getMetaData().getDriverName(),
                connection.getMetaData().getDriverVersion(),
                connection.getMetaData().getURL(),
                connection.getMetaData().getUserName(),
                connection.getTransactionIsolation()
            );
        } catch (SQLException e) {
            return String.format("❌ 获取数据库信息失败: %s", e.getMessage());
        }
    }
    
    /**
     * 健康检查
     */
    public String healthCheck() {
        try {
            boolean isConnected = testConnection().contains("✅");
            if (isConnected) {
                return "✅ PostgreSQL健康";
            } else {
                return "❌ PostgreSQL不健康";
            }
        } catch (Exception e) {
            log.error("PostgreSQL健康检查失败: {}", e.getMessage());
            return "❌ PostgreSQL检查失败";
        }
    }
}