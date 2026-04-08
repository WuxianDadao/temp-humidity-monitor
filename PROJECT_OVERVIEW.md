# 物联网温湿度实时监测系统 - 项目概况

## 📌 项目信息

| 项目 | 内容 |
|------|------|
| **项目名称** | 物联网温湿度实时监测系统 |
| **GitHub仓库** | https://github.com/WuxianDadao/temp-humidity-monitor |
| **项目ID** | project_20260327_150127 |
| **当前进度** | ~35% |
| **预估工期** | 2-4周 |

---

## 🎯 项目需求（原始需求）

用户原始需求：
> 我想开发一个物联网温湿度实时监测系统，具体需求如下：

1. **实时数据采集** — 传感器数据通过MQTT协议接入，支持温度、湿度等指标
2. **多网关管理** — 支持多个网关设备，每个网关下挂载多个传感器
3. **报警管理** — 支持阈值报警、报警规则配置、报警历史记录
4. **数据存储** — 时序数据存储在TDengine，业务数据存储在PostgreSQL
5. **缓存加速** — Redis缓存网关在线状态、实时数据
6. **RESTful API** — 提供完整的API接口，支持Swagger文档
7. **多租户支持** — 组织架构、用户权限管理

---

## 🏗️ 技术栈

| 组件 | 技术选型 | 状态 |
|------|---------|------|
| 后端框架 | Spring Boot 3.2.4 | ✅ 已集成 |
| 时序数据库 | TDengine 3.4.0.9 | ✅ 已验证连接 |
| 关系数据库 | PostgreSQL | ✅ 已配置 |
| 缓存 | Redis | ✅ 已配置 |
| 消息协议 | MQTT | ✅ 配置类已创建 |
| API文档 | Swagger/OpenAPI | ✅ 已配置 |
| 构建工具 | Maven | ✅ 编译通过 |

---

## 📊 当前完成进度

### ✅ 已完成里程碑

1. **技术选型确认** — Spring Boot + TDengine + PostgreSQL + Redis + MQTT
2. **项目骨架创建** — 完整的Maven项目结构
3. **TDengine集成** — JDBC驱动、多数据源配置、REST API端点
4. **TDengine连接测试** — 实际连接成功，版本3.4.0.9
5. **PostgreSQL Schema设计** — 24张业务表设计文档
6. **TDengine Schema设计** — 4个超表设计文档
7. **Entity实体类** — 37个实体类（用户、设备、传感器、报警等）
8. **DTO数据传输对象** — 57个DTO类
9. **Repository数据层** — 18个Repository接口
10. **Service服务层** — 核心服务接口定义（部分实现）
11. **Controller接口层** — 7个Controller，70+ API端点
12. **编译验证** — `mvn package` 编译成功 BUILD SUCCESS
13. **应用启动** — 使用setsid方式稳定运行在8080端口

### 🔴 阻塞问题（必须先解决）

**架构冲突问题** — 项目存在两套并行的Entity/Repository定义：

| 层级 | Root包 | PostgreSQL包 |
|------|--------|-------------|
| Entity | `entity.*` | `entity.postgresql.*` |
| Repository | `repository.*` | `repository.postgresql.*` |

**问题详情**：
- JPA配置扫描 `repository.postgresql.*`
- 但Service注入的是 `repository.*` (root) 的Repository
- 导致 "No qualifying bean" 错误
- 24个Service实现被禁用为 `.bak` 文件

**解决方案（二选一）**：
- **方案A**: 保留Root包，修改JpaEntityManagerConfig的EntityScan和RepositoryScan指向root包
- **方案B（推荐）**: 保留PostgreSQL包，修改所有Service的import和注入

---

## 📁 项目结构

```
temp-humidity-monitor/
├── src/main/java/com/iot/temphumidity/
│   ├── config/           # 配置类（CORS、JPA、Redis、MQTT、TDengine等）
│   ├── controller/       # REST API控制器
│   ├── dto/              # 数据传输对象（57个类）
│   │   ├── alarm/        # 报警相关DTO
│   │   ├── common/       # 通用响应DTO
│   │   ├── device/       # 设备DTO
│   │   ├── gateway/      # 网关DTO
│   │   ├── sensor/       # 传感器DTO
│   │   ├── user/         # 用户DTO
│   │   └── mqtt/        # MQTT消息DTO
│   ├── entity/           # JPA实体类
│   │   └── postgresql/   # PostgreSQL实体（被JPA扫描）
│   ├── enums/           # 枚举类（17个）
│   ├── exception/        # 异常类
│   ├── mapper/          # MyBatis Mapper
│   ├── pojo/            # 简单POJO对象
│   ├── repository/      # Spring Data JPA Repository
│   │   └── postgresql/  # PostgreSQL Repository
│   ├── service/         # 服务接口
│   │   ├── impl/        # 服务实现（.bak禁用状态）
│   │   └── postgresql/  # PostgreSQL服务
│   └── converter/       # 数据转换器
├── src/main/resources/
│   ├── application.yml  # 主配置文件
│   └── schema-postgresql.sql  # PostgreSQL建表脚本
├── docs/
│   ├── postgresql-schema-design.md   # PostgreSQL设计文档
│   └── tdengine-schema-design.md     # TDengine设计文档
├── pom.xml              # Maven配置
└── README.md            # 项目说明
```

---

## 🔌 数据库设计

### PostgreSQL（业务数据）

主要表结构：
- **用户管理**: users, roles, permissions, user_roles
- **设备管理**: devices, device_gateways, device_tags, sensors
- **网关管理**: gateways
- **报警管理**: alarm_rules, alarm_history, alarm_config, alarm_events
- **组织管理**: organizations, organization_members
- **系统管理**: system_configs, system_logs, dictionaries

### TDengine（时序数据）

超表设计：
- **sensor_data** — 传感器数据超表（温度、湿度等）
- **sensor_statistics** — 聚合统计数据
- **alarm_events** — 报警事件记录
- **device_status** — 设备心跳状态

---

## 📋 待完成任务（优先级排序）

### P0 - 阻塞问题（必须先解决）
1. **统一Entity/Repository架构** — 解决两套并行的包导致的Bean注入失败

### P1 - 核心功能
2. **完成Service业务实现** — 启用被禁用的ServiceImpl
3. **单元测试编写** — 为核心业务逻辑编写单元测试
4. **PostgreSQL建表脚本执行** — 初始化数据库表结构

### P2 - 完善功能
5. **MQTT消息处理器实现** — 处理设备上报的传感器数据
6. **报警规则引擎完善** — 阈值检查、报警触发逻辑
7. **数据转换器完善** — TDengine与PostgreSQL之间的数据流转

### P3 - 运维支持
8. **Docker部署配置完善** — docker-compose编排
9. **前端界面开发** — Vue.js管理后台
10. **集成测试** — 完整业务流程测试

---

## 🐛 已知问题

| 问题 | 优先级 | 状态 |
|------|--------|------|
| 架构冲突（两套Entity/Repository） | P0 | 🔴 未解决 |
| Service层24个实现类是.bak状态 | P0 | 🔴 未解决 |
| PostgreSQL数据库表未创建 | P1 | ⏳ 待执行 |
| MQTT消息处理器未实现 | P2 | ⏳ 待开发 |
| 前端界面未开发 | P3 | ⏳ 待开发 |

---

## 🔧 本地开发环境

### 依赖服务
- PostgreSQL: localhost:5432 (数据库: iot_db, 用户: iot_user)
- Redis: localhost:6379
- TDengine: localhost:6030 (数据库: iot_db)
- MQTT Broker: localhost:1883

### 启动命令
```bash
cd /root/.openclaw/workspace/spring-boot-temp-humidity
mvn package -Dmaven.test.skip=true
setsid java -jar target/temp-humidity-monitor-1.0.0.jar &
```

### 验证接口
```bash
curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/api/devices
# 预期返回: 401 (需要认证)
```

---

## 📝 文档位置

| 文档 | 路径 |
|------|------|
| PostgreSQL Schema设计 | `docs/postgresql-schema-design.md` |
| TDengine Schema设计 | `docs/tdengine-schema-design.md` |
| 项目README | `README.md` |
| 本概况文档 | `PROJECT_OVERVIEW.md` |

---

## 🚀 DeerFlow 2.0 继续开发指引

在另一台电脑的DeerFlow 2.0中操作步骤：

1. **克隆仓库**
   ```bash
   git clone https://github.com/WuxianDadao/temp-humidity-monitor.git
   cd temp-humidity-monitor
   ```

2. **阅读文档顺序**
   - 先读 `PROJECT_OVERVIEW.md` 了解全貌
   - 再读 `docs/postgresql-schema-design.md` 了解数据库设计
   - 再读 `docs/tdengine-schema-design.md` 了解时序库设计

3. **解决架构冲突**（P0优先级）
   - 这是阻塞所有Service层开发的核心问题
   - 必须先统一Entity/Repository包结构

4. **按P1→P2→P3顺序继续开发**
   - 完成Service实现
   - 编写单元测试
   - 开发MQTT消息处理
   - 开发前端界面

---

*最后更新: 2026-04-08*
*更新者: OpenClaw Assistant*
