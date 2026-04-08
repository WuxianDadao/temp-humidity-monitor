package com.iot.temphumidity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class TDengineConfig {

    @Value("${tdengine.url:jdbc:TAOS://localhost:6030/iot_db}")
    private String tdengineUrl;

    @Value("${tdengine.username:root}")
    private String tdengineUsername;

    @Value("${tdengine.password:taosdata}")
    private String tdenginePassword;

    @Bean
    public DataSource tdengineDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.taosdata.jdbc.TSDBDriver");
        dataSource.setUrl(tdengineUrl);
        dataSource.setUsername(tdengineUsername);
        dataSource.setPassword(tdenginePassword);
        return dataSource;
    }

    @Bean
    public JdbcTemplate tdengineJdbcTemplate() {
        return new JdbcTemplate(tdengineDataSource());
    }

    /**
     * 测试TDengine连接
     */
    @Bean
    public boolean testTDengineConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                tdengineUrl, tdengineUsername, tdenginePassword);
            
            log.info("✅ TDengine连接测试成功!");
            log.info("连接URL: {}", tdengineUrl);
            log.info("数据库: {}", connection.getCatalog());
            
            // 创建数据库（如果不存在）
            createDatabaseIfNotExists();
            
            connection.close();
            return true;
        } catch (SQLException e) {
            log.error("❌ TDengine连接失败: {}", e.getMessage());
            log.warn("⚠️ 请确保TDengine服务已启动并配置正确");
            log.warn("连接配置:");
            log.warn("URL: {}", tdengineUrl);
            log.warn("Username: {}", tdengineUsername);
            log.warn("Password: {}", tdenginePassword);
            return false;
        }
    }

    /**
     * 创建数据库
     */
    private void createDatabaseIfNotExists() {
        try {
            JdbcTemplate jdbcTemplate = tdengineJdbcTemplate();
            
            // 检查数据库是否存在
            jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS iot_db");
            log.info("✅ 数据库 iot_db 已准备就绪");
            
            // 切换到这个数据库
            jdbcTemplate.execute("USE iot_db");
            log.info("✅ 已切换到数据库: iot_db");
            
        } catch (Exception e) {
            log.error("❌ 创建数据库失败: {}", e.getMessage());
        }
    }

    /**
     * 初始化超表结构
     * 在应用启动时自动创建超表
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initSuperTable() {
        try {
            JdbcTemplate jdbcTemplate = tdengineJdbcTemplate();
            jdbcTemplate.execute("USE iot_db");
            
            // 创建传感器数据超表
            String createSuperTableSQL = """
                CREATE STABLE IF NOT EXISTS sensor_data (
                    ts TIMESTAMP,
                    temperature FLOAT,
                    humidity FLOAT,
                    voltage FLOAT,
                    rssi INT
                ) TAGS (
                    device_id NCHAR(32),
                    location NCHAR(64),
                    sensor_type NCHAR(32)
                )
                """;
            
            jdbcTemplate.execute(createSuperTableSQL);
            log.info("✅ 传感器数据超表 sensor_data 已创建");
            
            // 创建报警记录超表
            String createAlertSuperTableSQL = """
                CREATE STABLE IF NOT EXISTS alert_records (
                    ts TIMESTAMP,
                    alert_type NCHAR(32),
                    alert_value FLOAT,
                    alert_level INT,
                    alert_message NCHAR(256)
                ) TAGS (
                    device_id NCHAR(32),
                    alert_code NCHAR(32)
                )
                """;
            
            jdbcTemplate.execute(createAlertSuperTableSQL);
            log.info("✅ 报警记录超表 alert_records 已创建");
            
            log.info("🎉 TDengine数据库和超表初始化完成!");
            
        } catch (Exception e) {
            log.error("❌ 初始化超表失败: {}", e.getMessage());
        }
    }
}