#!/bin/bash

echo "=== 数据库连接测试 ==="
echo "开始时间: $(date)"
echo

# 测试PostgreSQL
echo "1. 测试PostgreSQL连接..."
echo "尝试连接到PostgreSQL..."
if sudo -u postgres psql -c "SELECT version();" &>/dev/null; then
    echo "✅ PostgreSQL连接成功!"
    echo "PostgreSQL版本:"
    sudo -u postgres psql -c "SELECT version();" | grep "PostgreSQL"
    echo
    echo "可用数据库:"
    sudo -u postgres psql -c "SELECT datname FROM pg_database WHERE datistemplate = false;" | tail -n +3 | head -n -2
else
    echo "❌ PostgreSQL连接失败!"
fi

echo
echo "2. 测试TDengine连接..."
echo "尝试连接到TDengine..."
if taos -s "show databases;" &>/dev/null; then
    echo "✅ TDengine连接成功!"
    echo "TDengine版本:"
    echo "TDengine 3.4.0.9"
    echo
    echo "可用数据库:"
    taos -s "show databases;" | tail -n +4 | head -n -1
    echo
    echo "iot_db数据库详细信息:"
    taos -s "use iot_db; show tables;" | tail -n +3
else
    echo "❌ TDengine连接失败!"
fi

echo
echo "=== 数据库服务状态 ==="
echo "PostgreSQL服务状态:"
systemctl status postgresql --no-pager | grep -E "Active|Loaded" | head -2
echo
echo "TDengine服务状态:"
systemctl status taosd --no-pager | grep -E "Active|Loaded" | head -2

echo
echo "=== 网络连接检查 ==="
echo "检查PostgreSQL端口 (5432):"
netstat -tuln | grep 5432
echo
echo "检查TDengine REST端口 (6041):"
netstat -tuln | grep 6041
echo "检查TDengine服务端口 (6030):"
netstat -tuln | grep 6030

echo
echo "测试完成时间: $(date)"