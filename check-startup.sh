#!/bin/bash

echo "检查Spring Boot应用启动问题..."
cd /root/.openclaw/workspace/spring-boot-temp-humidity

# 先编译
echo "1. 编译项目..."
mvn clean compile -DskipTests 2>&1 | grep -E "BUILD SUCCESS|BUILD FAILURE|ERROR"

echo ""
echo "2. 尝试启动应用（带详细日志）..."
# 使用-X查看详细错误
timeout 30 mvn spring-boot:run -DskipTests -X 2>&1 | grep -E "ERROR|Failed to start|Cannot connect|Connection refused|ConnectionPool|BeanCreationException|APPLICATION FAILED TO START" | head -20