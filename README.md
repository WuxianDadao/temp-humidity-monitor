# 物联网温湿度实时监测系统

## 🎯 项目概述

基于 Spring Boot 的物联网温湿度实时监测系统，采用多数据库架构处理不同类型的数据，支持 MQTT 协议设备接入。

**GitHub**: https://github.com/WuxianDadao/temp-humidity-monitor

## 🏗️ 技术架构

| 组件 | 技术选型 |
|------|---------|
| 后端框架 | Spring Boot 3.2.4 |
| 时序数据库 | TDengine 3.4.0.9 |
| 关系数据库 | PostgreSQL |
| 缓存 | Redis |
| 消息协议 | MQTT |
| API文档 | Swagger/OpenAPI |

## 📊 项目状态

- **进度**: ~35%
- **阶段**: Service层业务实现阶段
- **编译状态**: ✅ BUILD SUCCESS
- **运行状态**: ✅ 8080端口运行中

## 🚀 快速开始

### 启动应用
```bash
cd temp-humidity-monitor
mvn package -Dmaven.test.skip=true
setsid java -jar target/temp-humidity-monitor-1.0.0.jar &
```

### 验证运行
```bash
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/devices
# 返回 401 表示正常运行（需要认证）
```

## 📁 项目结构

```
src/main/java/com/iot/temphumidity/
├── config/           # 配置类（CORS、JPA、Redis、MQTT、TDengine）
├── controller/        # REST API控制器（7个Controller，70+端点）
├── dto/              # 数据传输对象（57个类）
├── entity/           # JPA实体类（37个）
├── enums/            # 枚举类（17个）
├── exception/         # 异常类
├── mapper/           # MyBatis Mapper
├── repository/       # Spring Data JPA Repository（18个）
├── service/          # 服务接口和实现
└── converter/        # 数据转换器
```

## 📚 文档

| 文档 | 说明 |
|------|------|
| `PROJECT_OVERVIEW.md` | **项目概况** - 完整需求、进度、待办事项 |
| `docs/postgresql-schema-design.md` | PostgreSQL 数据库Schema设计（24张表） |
| `docs/tdengine-schema-design.md` | TDengine 时序数据库Schema设计（4个超表） |

## 🔴 阻塞问题

项目存在**架构冲突**：两套并行的 Entity/Repository 定义导致 Service 层被禁用。

**解决方案**：需要统一 `entity.*` / `repository.*` 与 `entity.postgresql.*` / `repository.postgresql.*` 两套包的架构。

详见 `PROJECT_OVERVIEW.md` 中的「阻塞问题」章节。

## 📋 待完成任务

1. **P0**: 统一Entity/Repository架构（阻塞问题）
2. **P1**: 完成Service业务实现
3. **P1**: 单元测试编写
4. **P2**: MQTT消息处理器实现
5. **P2**: 报警规则引擎完善
6. **P3**: 前端界面开发

## 🛠️ 开发环境依赖

- Java 17+
- Maven 3.8+
- PostgreSQL (localhost:5432)
- TDengine (localhost:6030)
- Redis (localhost:6379)
- MQTT Broker (localhost:1883)

## 📝 配置说明

数据库连接配置在 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    postgresql:
      url: jdbc:postgresql://localhost:5432/iot_db
      username: iot_user
      password: your_password
  data:
    redis:
      host: localhost
      port: 6379
```

---

*最后更新: 2026-04-08*
