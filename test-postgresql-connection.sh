#!/bin/bash

# PostgreSQL连接测试脚本
# 测试PostgreSQL数据库连接和实体类配置

echo "========================================="
echo "PostgreSQL连接测试脚本"
echo "开始时间: $(date)"
echo "========================================="

# 检查Docker容器状态
echo ""
echo "1. 检查Docker容器状态:"
echo "-----------------------------------------"
docker ps | grep -E "(postgres|redis)"

# 检查PostgreSQL服务
echo ""
echo "2. 检查PostgreSQL服务:"
echo "-----------------------------------------"
if pg_isready -h localhost -p 5432 2>/dev/null; then
    echo "✓ PostgreSQL服务正在运行"
    
    # 测试连接
    echo ""
    echo "3. 测试PostgreSQL连接:"
    echo "-----------------------------------------"
    PGPASSWORD=iot_password_secure psql -h localhost -p 5432 -U iot_user -d iot_db -c "SELECT version();" 2>&1
    if [ $? -eq 0 ]; then
        echo "✓ PostgreSQL连接成功"
    else
        echo "✗ PostgreSQL连接失败"
    fi
else
    echo "✗ PostgreSQL服务未运行"
    
    echo ""
    echo "3. 尝试启动PostgreSQL容器:"
    echo "-----------------------------------------"
    
    # 检查是否有现有容器
    if docker ps -a | grep -q iot-postgres; then
        echo "发现现有容器，尝试启动..."
        docker start iot-postgres
        sleep 3
    else
        echo "没有现有容器，尝试创建新容器..."
        docker run -d --name iot-postgres -p 5432:5432 \
          -e POSTGRES_DB=iot_db \
          -e POSTGRES_USER=iot_user \
          -e POSTGRES_PASSWORD=iot_password_secure \
          -v postgres_iot_data:/var/lib/postgresql/data \
          postgres:15-alpine 2>/dev/null
        
        if [ $? -eq 0 ]; then
            echo "✓ PostgreSQL容器创建成功，等待启动..."
            sleep 10
        else
            echo "✗ PostgreSQL容器创建失败"
            echo "可能是网络问题或镜像不可用"
        fi
    fi
fi

# 检查Spring Boot项目编译状态
echo ""
echo "4. 检查Spring Boot项目编译状态:"
echo "-----------------------------------------"
cd /root/.openclaw/workspace/spring-boot-temp-humidity
if mvn compile -q 2>&1 | grep -q "BUILD SUCCESS"; then
    echo "✓ 项目编译成功"
else
    echo "✗ 项目编译失败"
    echo "编译错误信息:"
    mvn compile 2>&1 | grep -A5 -B5 "ERROR"
fi

# 检查实体类
echo ""
echo "5. 检查PostgreSQL实体类:"
echo "-----------------------------------------"
ENTITY_COUNT=$(find src/main/java/com/iot/temphumidity/entity/postgresql -name "*.java" 2>/dev/null | wc -l)
echo "PostgreSQL实体类数量: $ENTITY_COUNT"

if [ $ENTITY_COUNT -gt 0 ]; then
    echo "找到的实体类:"
    find src/main/java/com/iot/temphumidity/entity/postgresql -name "*.java" | xargs -I {} basename {}
fi

# 检查Repository类
echo ""
echo "6. 检查Repository类:"
echo "-----------------------------------------"
REPO_COUNT=$(find src/main/java/com/iot/temphumidity/repository/postgresql -name "*.java" 2>/dev/null | wc -l)
echo "PostgreSQL Repository类数量: $REPO_COUNT"

# 测试配置文件
echo ""
echo "7. 检查PostgreSQL配置:"
echo "-----------------------------------------"
if [ -f src/main/resources/application.yml ]; then
    echo "application.yml配置文件存在"
    grep -A5 -B5 "postgresql" src/main/resources/application.yml || echo "未找到PostgreSQL配置"
else
    echo "✗ application.yml配置文件不存在"
fi

# 生成测试报告
echo ""
echo "========================================="
echo "测试报告"
echo "完成时间: $(date)"
echo "========================================="

# 检查测试控制器
echo ""
echo "8. 检查PostgreSQL测试控制器:"
echo "-----------------------------------------"
if [ -f src/main/java/com/iot/temphumidity/controller/test/PostgreSQLTestController.java ]; then
    echo "✓ PostgreSQL测试控制器已创建"
    echo "测试端点:"
    echo "  - GET /api/test/postgresql/connection    - 测试连接"
    echo "  - GET /api/test/postgresql/jpa          - 测试JPA配置"
    echo "  - GET /api/test/postgresql/jdbc         - 测试JDBC配置"
    echo "  - POST /api/test/postgresql/gateway/crud - 测试CRUD操作"
    echo "  - GET /api/test/postgresql/config       - 查看配置信息"
    echo "  - GET /api/test/postgresql/health       - 健康检查"
else
    echo "✗ PostgreSQL测试控制器未创建"
fi

echo ""
echo "9. 下一步建议:"
echo "-----------------------------------------"
echo "如果PostgreSQL服务正常运行:"
echo "  1. 启动Spring Boot应用: ./start-app.sh"
echo "  2. 访问测试端点验证连接"
echo "  3. 创建数据库表: mvn spring-boot:run 会自动创建表"
echo ""
echo "如果PostgreSQL服务未运行:"
echo "  1. 确保Docker服务正常运行"
echo "  2. 检查网络连接"
echo "  3. 使用备用数据库或本地安装PostgreSQL"

echo ""
echo "========================================="
echo "测试脚本完成"
echo "========================================="