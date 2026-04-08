# 物联网温湿度监测系统

## 🎯 项目概述
基于Spring Boot的物联网温湿度实时监测系统，采用多数据库架构处理不同类型的数据。

## 🏗️ 技术架构
- **后端框架**: Spring Boot 3.2.4
- **前端框架**: Vue.js (待添加)
- **数据库架构**:
  - **PostgreSQL**: 业务数据 (用户、设备、配置)
  - **TDengine**: 时序数据 (传感器读数)
  - **Redis**: 缓存和实时状态
- **消息协议**: MQTT (设备通信)

## 📊 当前状态
**项目阶段**: TDengine集成完成
**最后更新**: 2026-03-27 16:02
**状态**: ✅ Spring Boot骨架 + TDengine配置完成

## 🚀 已完成工作

### ✅ 1. Spring Boot项目骨架
- 创建了完整的Maven项目结构
- 配置了多数据库依赖 (PostgreSQL + TDengine + Redis)
- 设置了应用主类和基础配置

### ✅ 2. TDengine集成
- 添加TDengine JDBC驱动依赖
- 创建TDengine配置类 (`TDengineConfig.java`)
- 自动创建数据库和超表结构
- 配置连接池和数据源

### ✅ 3. 多数据库配置
- **PostgreSQL**: 业务数据存储
- **TDengine**: 传感器时序数据存储
- **Redis**: 实时状态缓存
- 完整的`application.yml`配置文件

### ✅ 4. 测试和验证
- 创建TDengine测试服务 (`TDengineTestService.java`)
- 创建REST测试接口 (`TDengineTestController.java`)
- 支持连接测试、版本查询、数据操作

## 🔧 项目结构
```
spring-boot-temp-humidity/
├── src/main/java/com/iot/temphumidity/
│   ├── TempHumidityMonitorApplication.java  # 应用主类
│   ├── config/
│   │   └── TDengineConfig.java              # TDengine配置
│   ├── controller/
│   │   └── TDengineTestController.java      # 测试接口
│   └── service/
│       └── TDengineTestService.java         # 测试服务
├── src/main/resources/
│   └── application.yml                      # 应用配置
└── pom.xml                                  # Maven配置
```

## 📋 测试接口
应用启动后，可以通过以下接口测试TDengine:

1. **连接状态**: `GET /api/tdengine/status`
2. **完整测试**: `POST /api/tdengine/test`
3. **数据库信息**: `GET /api/tdengine/database-info`
4. **创建测试数据**: `POST /api/tdengine/test-data`
5. **健康检查**: `GET /api/tdengine/health`

## 🎯 下一步任务

### 🔴 高优先级
1. **测试TDengine连接** - 验证数据库服务是否正常
2. **设计TDengine超表Schema** - 完善传感器数据结构
3. **设计PostgreSQL Schema** - 业务数据表设计

### 🟡 中优先级
4. **创建传感器数据模型** - Java实体类
5. **实现MQTT客户端** - 设备数据接收
6. **实现数据存储服务** - 双数据库写入

### 🔵 低优先级
7. **创建REST API** - 数据查询接口
8. **实现报警逻辑** - 阈值检测
9. **添加监控和日志** - 系统监控

## 🐳 快速开始

### 1. 环境要求
- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- TDengine 3.0+
- Redis 6+

### 2. 启动服务
```bash
# 构建项目
mvn clean package

# 运行应用
java -jar target/temp-humidity-monitor-1.0.0.jar
```

### 3. 测试TDengine
```bash
# 测试连接
curl http://localhost:8080/api/tdengine/status

# 运行完整测试
curl -X POST http://localhost:8080/api/tdengine/test
```

## 📝 配置说明
详细配置见 `src/main/resources/application.yml`

## 🔗 相关项目
- **DeerFlow**: 项目管理和决策系统
- **TDengine**: 时序数据库
- **Spring Boot**: 后端框架

---
*最后更新: 2026-03-27 16:02*