package com.iot.temphumidity.controller.test;

import com.iot.temphumidity.entity.postgresql.GatewayEntity;
import com.iot.temphumidity.repository.postgresql.GatewayRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * PostgreSQL数据库连接测试控制器
 * 
 * 提供PostgreSQL连接测试、配置验证和基本CRUD测试
 */
@RestController
@RequestMapping("/api/test/postgresql")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PostgreSQL测试", description = "PostgreSQL数据库连接和配置测试接口")
public class PostgreSQLTestControllerAlt {
    
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;
    private final GatewayRepository gatewayRepository;
    
    /**
     * 测试PostgreSQL连接状态
     */
    @GetMapping("/connection")
    @Operation(summary = "测试PostgreSQL连接状态", description = "检查PostgreSQL数据库连接是否正常")
    public ResponseEntity<Map<String, Object>> testConnection() {
        log.info("测试PostgreSQL连接状态");
        
        Map<String, Object> result = new HashMap<>();
        
        try (Connection connection = dataSource.getConnection()) {
            result.put("status", "success");
            result.put("message", "PostgreSQL连接成功");
            result.put("url", connection.getMetaData().getURL());
            result.put("driver", connection.getMetaData().getDriverName());
            result.put("version", connection.getMetaData().getDatabaseProductVersion());
            result.put("connected", !connection.isClosed());
            
            log.info("PostgreSQL连接测试成功: {}", result);
            
            return ResponseEntity.ok(result);
        } catch (SQLException e) {
            log.error("PostgreSQL连接测试失败", e);
            
            result.put("status", "error");
            result.put("message", "PostgreSQL连接失败: " + e.getMessage());
            result.put("error", e.getClass().getName());
            
            return ResponseEntity.status(500).body(result);
        }
    }
    
    /**
     * 测试JPA/Hibernate配置
     */
    @GetMapping("/jpa")
    @Operation(summary = "测试JPA/Hibernate配置", description = "检查JPA和Hibernate配置是否正确")
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> testJpa() {
        log.info("测试JPA/Hibernate配置");
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 测试EntityManager
            boolean entityManagerOpen = entityManager.isOpen();
            String persistenceUnit = entityManager.getEntityManagerFactory().getProperties().toString();
            
            // 测试GatewayEntity映射
            long gatewayCount = gatewayRepository.count();
            
            result.put("status", "success");
            result.put("message", "JPA/Hibernate配置正确");
            result.put("entityManagerOpen", entityManagerOpen);
            result.put("gatewayCount", gatewayCount);
            result.put("entityManagerProperties", persistenceUnit.substring(0, Math.min(persistenceUnit.length(), 200)));
            
            log.info("JPA/Hibernate配置测试成功: 网关数量={}", gatewayCount);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("JPA/Hibernate配置测试失败", e);
            
            result.put("status", "error");
            result.put("message", "JPA/Hibernate配置测试失败: " + e.getMessage());
            result.put("error", e.getClass().getName());
            
            return ResponseEntity.status(500).body(result);
        }
    }
    
    /**
     * 测试JdbcTemplate
     */
    @GetMapping("/jdbc")
    @Operation(summary = "测试JdbcTemplate", description = "检查JdbcTemplate配置和基本查询")
    public ResponseEntity<Map<String, Object>> testJdbc() {
        log.info("测试JdbcTemplate");
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 测试简单查询
            String databaseName = jdbcTemplate.queryForObject(
                "SELECT current_database()", String.class
            );
            
            String postgresVersion = jdbcTemplate.queryForObject(
                "SELECT version()", String.class
            );
            
            int tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public'", 
                Integer.class
            );
            
            result.put("status", "success");
            result.put("message", "JdbcTemplate测试成功");
            result.put("databaseName", databaseName);
            result.put("postgresVersion", postgresVersion);
            result.put("tableCount", tableCount);
            
            log.info("JdbcTemplate测试成功: 数据库={}, 表数量={}", databaseName, tableCount);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("JdbcTemplate测试失败", e);
            
            result.put("status", "error");
            result.put("message", "JdbcTemplate测试失败: " + e.getMessage());
            result.put("error", e.getClass().getName());
            
            return ResponseEntity.status(500).body(result);
        }
    }
    
    /**
     * 测试网关表CRUD操作
     */
    @PostMapping("/gateway/crud")
    @Operation(summary = "测试网关表CRUD操作", description = "测试网关表的创建、读取、更新、删除操作")
    @Transactional
    public ResponseEntity<Map<String, Object>> testGatewayCrud() {
        log.info("测试网关表CRUD操作");
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 创建测试网关
            GatewayEntity testGateway = new GatewayEntity();
            testGateway.setIccid("TEST_ICCID_" + System.currentTimeMillis());
            testGateway.setName("测试网关");
            testGateway.setModel("TEST_MODEL");
            testGateway.setManufacturer("测试厂商");
            testGateway.setStatus(com.iot.temphumidity.enums.GatewayStatus.OFFLINE);
            testGateway.setUserId(1L);
            testGateway.setLocation("测试位置");
            testGateway.setRemarks("CRUD测试网关");
            // createdAt和updatedAt由BaseEntity自动管理，不需要手动设置
            
            GatewayEntity savedGateway = gatewayRepository.save(testGateway);
            Long gatewayId = savedGateway.getId();
            
            result.put("createSuccess", true);
            result.put("createdGatewayId", gatewayId);
            result.put("createdIccid", savedGateway.getIccid());
            
            log.info("创建网关成功: ID={}, ICCID={}", gatewayId, savedGateway.getIccid());
            
            // 2. 读取网关
            GatewayEntity readGateway = gatewayRepository.findById(gatewayId)
                .orElseThrow(() -> new RuntimeException("网关读取失败"));
            
            result.put("readSuccess", true);
            result.put("readGatewayName", readGateway.getName());
            
            log.info("读取网关成功: ID={}, 名称={}", gatewayId, readGateway.getName());
            
            // 3. 更新网关
            readGateway.setName("更新后的测试网关");
            // updatedAt字段由@UpdateTimestamp自动管理，不需要手动设置
            GatewayEntity updatedGateway = gatewayRepository.save(readGateway);
            
            result.put("updateSuccess", true);
            result.put("updatedGatewayName", updatedGateway.getName());
            
            log.info("更新网关成功: ID={}, 新名称={}", gatewayId, updatedGateway.getName());
            
            // 4. 删除网关
            gatewayRepository.deleteById(gatewayId);
            
            // 验证删除
            boolean existsAfterDelete = gatewayRepository.existsById(gatewayId);
            
            result.put("deleteSuccess", !existsAfterDelete);
            result.put("existsAfterDelete", existsAfterDelete);
            
            if (!existsAfterDelete) {
                log.info("删除网关成功: ID={}", gatewayId);
            } else {
                log.warn("删除网关后仍然存在: ID={}", gatewayId);
            }
            
            result.put("status", "success");
            result.put("message", "网关CRUD测试完成");
            result.put("timestamp", LocalDateTime.now().toString());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("网关CRUD测试失败", e);
            
            result.put("status", "error");
            result.put("message", "网关CRUD测试失败: " + e.getMessage());
            result.put("error", e.getClass().getName());
            
            return ResponseEntity.status(500).body(result);
        }
    }
    
    /**
     * 获取PostgreSQL配置信息
     */
    @GetMapping("/config")
    @Operation(summary = "获取PostgreSQL配置信息", description = "查看当前PostgreSQL连接配置信息")
    public ResponseEntity<Map<String, Object>> getConfigInfo() {
        log.info("获取PostgreSQL配置信息");
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取数据源信息
            String url = dataSource.getConnection().getMetaData().getURL();
            String username = dataSource.getConnection().getMetaData().getUserName();
            
            result.put("status", "success");
            result.put("url", url);
            result.put("username", username);
            result.put("dataSourceClass", dataSource.getClass().getName());
            
            // 获取JPA配置信息
            Map<String, Object> jpaProperties = entityManager.getEntityManagerFactory().getProperties();
            result.put("jpaProperties", jpaProperties);
            
            log.info("PostgreSQL配置信息获取成功");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("获取PostgreSQL配置信息失败", e);
            
            result.put("status", "error");
            result.put("message", "获取配置信息失败: " + e.getMessage());
            
            return ResponseEntity.status(500).body(result);
        }
    }
    
    /**
     * 健康检查端点
     */
    @GetMapping("/health")
    @Operation(summary = "PostgreSQL健康检查", description = "检查PostgreSQL数据库的整体健康状况")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        log.info("PostgreSQL健康检查");
        
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> checks = new HashMap<>();
        
        try {
            // 检查连接
            try (Connection conn = dataSource.getConnection()) {
                checks.put("connection", "OK");
                checks.put("database", conn.getCatalog());
            }
            
            // 检查JPA
            try {
                gatewayRepository.count();
                checks.put("jpa", "OK");
            } catch (Exception e) {
                checks.put("jpa", "ERROR: " + e.getMessage());
            }
            
            // 检查JDBC
            try {
                jdbcTemplate.queryForObject("SELECT 1", Integer.class);
                checks.put("jdbc", "OK");
            } catch (Exception e) {
                checks.put("jdbc", "ERROR: " + e.getMessage());
            }
            
            // 检查EntityManager
            try {
                boolean isOpen = entityManager.isOpen();
                checks.put("entityManager", isOpen ? "OK" : "CLOSED");
            } catch (Exception e) {
                checks.put("entityManager", "ERROR: " + e.getMessage());
            }
            
            // 汇总结果
            long successCount = checks.values().stream()
                .filter(v -> v.toString().contains("OK"))
                .count();
            
            result.put("status", successCount == checks.size() ? "HEALTHY" : "DEGRADED");
            result.put("checks", checks);
            result.put("successCount", successCount);
            result.put("totalChecks", checks.size());
            result.put("timestamp", LocalDateTime.now().toString());
            
            log.info("PostgreSQL健康检查完成: 状态={}, 成功={}/{}", 
                result.get("status"), successCount, checks.size());
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("PostgreSQL健康检查失败", e);
            
            result.put("status", "UNHEALTHY");
            result.put("error", e.getMessage());
            result.put("checks", checks);
            
            return ResponseEntity.status(503).body(result);
        }
    }
}