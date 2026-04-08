#!/bin/bash

echo "=== Spring Boot应用启动测试 ==="
echo "开始时间: $(date)"
echo

# 1. 检查Maven构建状态
echo "1. 检查Maven项目状态..."
if [ -f "pom.xml" ]; then
    echo "✅ pom.xml存在"
    echo "项目信息:"
    grep -E "artifactId|version|name" pom.xml | head -3
else
    echo "❌ pom.xml不存在!"
    exit 1
fi

echo
echo "2. 检查依赖下载状态..."
if [ -d "~/.m2/repository" ]; then
    echo "Maven本地仓库存在"
else
    echo "需要下载依赖..."
fi

echo
echo "3. 尝试编译项目..."
mvn clean compile -q
if [ $? -eq 0 ]; then
    echo "✅ 项目编译成功!"
else
    echo "❌ 项目编译失败!"
    echo "尝试修复编译错误..."
    # 这里可以添加编译错误修复逻辑
fi

echo
echo "4. 检查数据库配置..."
echo "PostgreSQL配置:"
grep -A2 "spring.datasource.url" src/main/resources/application.yml || echo "未找到PostgreSQL配置"

echo
echo "TDengine配置:"
grep -A1 "tdengine.jdbc.url" src/main/resources/application.yml || echo "未找到TDengine配置"

echo
echo "5. 测试数据库连接..."
echo "测试PostgreSQL连接:"
sudo -u postgres psql -d iot_db -c "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'iot';" 2>/dev/null || echo "PostgreSQL连接失败"

echo
echo "测试TDengine连接:"
taos -s "use iot_db; select count(*) from sensor_data;" 2>/dev/null || echo "TDengine连接失败"

echo
echo "6. 启动Spring Boot应用..."
echo "注意：这可能需要几分钟时间..."
echo "启动日志将输出到 app_startup.log"

# 在后台启动应用
nohup mvn spring-boot:run > app_startup.log 2>&1 &
APP_PID=$!

echo "应用启动中，进程ID: $APP_PID"
echo "等待30秒让应用启动..."

sleep 30

# 检查应用是否启动成功
if ps -p $APP_PID > /dev/null; then
    echo "✅ Spring Boot应用启动成功!"
    echo "进程ID: $APP_PID"
    
    echo
    echo "7. 测试应用健康检查..."
    echo "等待额外10秒让应用完全启动..."
    sleep 10
    
    # 测试健康检查端点
    if curl -s http://localhost:8080/actuator/health > /dev/null; then
        echo "✅ 健康检查端点访问成功!"
        curl -s http://localhost:8080/actuator/health | python3 -m json.tool 2>/dev/null || echo "健康检查响应: $(curl -s http://localhost:8080/actuator/health | head -100)"
    else
        echo "⚠️ 健康检查端点访问失败，尝试其他端点..."
        if curl -s http://localhost:8080/actuator/info > /dev/null; then
            echo "✅ Actuator info端点访问成功!"
        else
            echo "❌ Actuator端点不可用"
        fi
    fi
    
    echo
    echo "8. 停止应用..."
    kill $APP_PID
    wait $APP_PID 2>/dev/null
    echo "应用已停止"
else
    echo "❌ Spring Boot应用启动失败!"
    echo "查看启动日志:"
    tail -50 app_startup.log
fi

echo
echo "测试完成时间: $(date)"