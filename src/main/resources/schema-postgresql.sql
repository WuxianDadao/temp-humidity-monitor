-- PostgreSQL 数据库初始化脚本
-- 物联网温湿度监测系统
-- 创建时间: 2026-04-01

-- 创建数据库
CREATE DATABASE iot_monitoring
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- 连接到新建的数据库
\c iot_monitoring;

-- 创建扩展
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE EXTENSION IF NOT EXISTS "postgis";

-- 1. 用户表
CREATE TABLE users (
    user_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(128) NOT NULL UNIQUE,
    phone VARCHAR(20) UNIQUE,
    display_name VARCHAR(128),
    avatar_url VARCHAR(512),
    role VARCHAR(16) DEFAULT 'USER' CHECK (role IN ('SUPER_ADMIN', 'ADMIN', 'USER', 'GUEST', 'API_USER')),
    status VARCHAR(16) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED', 'PENDING', 'LOCKED', 'DELETED')),
    email_verified BOOLEAN DEFAULT false,
    phone_verified BOOLEAN DEFAULT false,
    last_login_at TIMESTAMP,
    last_login_ip VARCHAR(45),
    login_count INTEGER DEFAULT 0,
    failed_login_attempts INTEGER DEFAULT 0,
    account_locked_until TIMESTAMP,
    password_changed_at TIMESTAMP,
    password_expires_at TIMESTAMP,
    mfa_enabled BOOLEAN DEFAULT false,
    mfa_secret VARCHAR(32),
    timezone VARCHAR(64),
    language VARCHAR(8),
    preferences JSONB,
    notes TEXT,
    invitation_code VARCHAR(32),
    invited_by UUID REFERENCES users(user_id),
    department VARCHAR(128),
    position VARCHAR(128),
    company VARCHAR(128),
    address VARCHAR(255),
    city VARCHAR(64),
    state VARCHAR(64),
    country VARCHAR(64),
    postal_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- 2. 网关表
CREATE TABLE gateways (
    gateway_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    gateway_sn VARCHAR(64) NOT NULL UNIQUE,
    gateway_name VARCHAR(128) NOT NULL,
    iccid VARCHAR(20) NOT NULL UNIQUE,
    imei VARCHAR(15) UNIQUE,
    owner_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    status VARCHAR(16) DEFAULT 'INACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'MAINTENANCE', 'OFFLINE', 'FAULTY')),
    network_type VARCHAR(16),
    signal_strength INTEGER,
    battery_level INTEGER,
    battery_voltage DECIMAL(5,2),
    temperature DECIMAL(5,2),
    humidity DECIMAL(5,2),
    firmware_version VARCHAR(32),
    hardware_version VARCHAR(32),
    manufacturer VARCHAR(64),
    model VARCHAR(64),
    serial_number VARCHAR(32),
    location VARCHAR(255),
    latitude DECIMAL(10,6),
    longitude DECIMAL(10,6),
    altitude DECIMAL(8,2),
    last_heartbeat TIMESTAMP,
    last_data_received TIMESTAMP,
    last_config_update TIMESTAMP,
    data_interval_seconds INTEGER DEFAULT 300,
    heartbeat_interval_seconds INTEGER DEFAULT 600,
    max_retry_count INTEGER DEFAULT 3,
    retry_interval_seconds INTEGER DEFAULT 30,
    alarm_enabled BOOLEAN DEFAULT true,
    alarm_config JSONB,
    metadata JSONB,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- 3. 设备表
CREATE TABLE devices (
    device_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    gateway_id UUID NOT NULL REFERENCES gateways(gateway_id) ON DELETE CASCADE,
    iccid VARCHAR(20) NOT NULL UNIQUE,
    imei VARCHAR(15) UNIQUE,
    device_name VARCHAR(128) NOT NULL,
    device_type VARCHAR(32) NOT NULL,
    status VARCHAR(16) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'MAINTENANCE', 'OFFLINE', 'FAULTY', 'DECOMMISSIONED', 'LOST', 'DESTROYED')),
    location VARCHAR(255),
    latitude DECIMAL(10,6),
    longitude DECIMAL(10,6),
    manufacturer VARCHAR(64),
    model VARCHAR(64),
    serial_number VARCHAR(32),
    firmware_version VARCHAR(32),
    hardware_version VARCHAR(32),
    network_type VARCHAR(16),
    signal_strength INTEGER,
    battery_level INTEGER,
    battery_voltage DECIMAL(5,2),
    temperature DECIMAL(5,2),
    humidity DECIMAL(5,2),
    last_data_received TIMESTAMP,
    last_config_update TIMESTAMP,
    data_interval_seconds INTEGER DEFAULT 300,
    heartbeat_interval_seconds INTEGER DEFAULT 600,
    max_retry_count INTEGER DEFAULT 3,
    retry_interval_seconds INTEGER DEFAULT 30,
    alarm_enabled BOOLEAN DEFAULT true,
    alarm_config JSONB,
    metadata JSONB,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- 4. 传感器标签表
CREATE TABLE sensor_tags (
    sensor_tag_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    device_id UUID NOT NULL REFERENCES devices(device_id) ON DELETE CASCADE,
    sensor_sn VARCHAR(64) NOT NULL UNIQUE,
    sensor_name VARCHAR(128) NOT NULL,
    sensor_type VARCHAR(32) NOT NULL,
    status VARCHAR(16) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'CALIBRATING', 'MAINTENANCE', 'FAULTY', 'LOST')),
    calibration_status INTEGER DEFAULT 0,
    calibration_date DATE,
    measurement_unit VARCHAR(16),
    min_value_range DECIMAL(10,2),
    max_value_range DECIMAL(10,2),
    accuracy DECIMAL(5,2),
    resolution DECIMAL(5,2),
    sample_rate INTEGER,
    location VARCHAR(255),
    latitude DECIMAL(10,6),
    longitude DECIMAL(10,6),
    altitude DECIMAL(8,2),
    installed_at TIMESTAMP,
    last_calibration_at TIMESTAMP,
    manufacturer VARCHAR(64),
    model VARCHAR(64),
    serial_number VARCHAR(32),
    firmware_version VARCHAR(32),
    hardware_version VARCHAR(32),
    metadata JSONB,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- 5. 报警表
CREATE TABLE alarms (
    alarm_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    alarm_type VARCHAR(32) NOT NULL,
    alarm_level VARCHAR(16) NOT NULL CHECK (alarm_level IN ('INFO', 'WARNING', 'MINOR', 'MAJOR', 'CRITICAL')),
    alarm_status VARCHAR(16) DEFAULT 'NEW' CHECK (alarm_status IN ('NEW', 'PENDING', 'ACKNOWLEDGED', 'RESOLVED', 'IGNORED', 'FALSE_ALARM', 'EXPIRED', 'CLOSED', 'ESCALATED', 'INVESTIGATING')),
    source_type VARCHAR(32) NOT NULL,
    source_id VARCHAR(128) NOT NULL,
    sensor_tag_id UUID REFERENCES sensor_tags(sensor_tag_id),
    device_id UUID REFERENCES devices(device_id),
    gateway_id UUID REFERENCES gateways(gateway_id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    trigger_value DECIMAL(20,6),
    threshold_value DECIMAL(20,6),
    trigger_time TIMESTAMP NOT NULL,
    acknowledge_time TIMESTAMP,
    acknowledge_by UUID REFERENCES users(user_id),
    resolved_time TIMESTAMP,
    resolved_by UUID REFERENCES users(user_id),
    resolution_notes TEXT,
    escalation_level INTEGER DEFAULT 1,
    notification_sent BOOLEAN DEFAULT false,
    notification_count INTEGER DEFAULT 0,
    last_notification_time TIMESTAMP,
    metadata JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- 6. 操作日志表
CREATE TABLE operation_logs (
    log_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id UUID REFERENCES users(user_id),
    operation_type VARCHAR(32) NOT NULL,
    operation_target VARCHAR(64) NOT NULL,
    target_id VARCHAR(128) NOT NULL,
    ip_address VARCHAR(45),
    user_agent TEXT,
    request_method VARCHAR(10),
    request_url TEXT,
    request_parameters TEXT,
    request_body TEXT,
    response_status INTEGER,
    response_body TEXT,
    execution_time_ms INTEGER,
    success BOOLEAN DEFAULT true,
    error_message TEXT,
    stack_trace TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引
-- 用户表索引
CREATE INDEX idx_users_role_status ON users(role, status);
CREATE INDEX idx_users_created_at ON users(created_at DESC);
CREATE INDEX idx_users_deleted_at ON users(deleted_at);

-- 网关表索引
CREATE INDEX idx_gateways_owner_id ON gateways(owner_id);
CREATE INDEX idx_gateways_status ON gateways(status);
CREATE INDEX idx_gateways_last_heartbeat ON gateways(last_heartbeat DESC);
CREATE INDEX idx_gateways_created_at ON gateways(created_at DESC);
CREATE INDEX idx_gateways_deleted_at ON gateways(deleted_at);

-- 设备表索引
CREATE INDEX idx_devices_gateway_id ON devices(gateway_id);
CREATE INDEX idx_devices_status ON devices(status);
CREATE INDEX idx_devices_last_data_received ON devices(last_data_received DESC);
CREATE INDEX idx_devices_created_at ON devices(created_at DESC);
CREATE INDEX idx_devices_deleted_at ON devices(deleted_at);

-- 传感器标签表索引
CREATE INDEX idx_sensor_tags_device_id ON sensor_tags(device_id);
CREATE INDEX idx_sensor_tags_status ON sensor_tags(status);
CREATE INDEX idx_sensor_tags_sensor_type ON sensor_tags(sensor_type);
CREATE INDEX idx_sensor_tags_created_at ON sensor_tags(created_at DESC);
CREATE INDEX idx_sensor_tags_deleted_at ON sensor_tags(deleted_at);

-- 报警表索引
CREATE INDEX idx_alarms_alarm_level ON alarms(alarm_level);
CREATE INDEX idx_alarms_alarm_status ON alarms(alarm_status);
CREATE INDEX idx_alarms_trigger_time ON alarms(trigger_time DESC);
CREATE INDEX idx_alarms_source ON alarms(source_type, source_id);
CREATE INDEX idx_alarms_sensor_tag_id ON alarms(sensor_tag_id);
CREATE INDEX idx_alarms_device_id ON alarms(device_id);
CREATE INDEX idx_alarms_gateway_id ON alarms(gateway_id);
CREATE INDEX idx_alarms_created_at ON alarms(created_at DESC);
CREATE INDEX idx_alarms_deleted_at ON alarms(deleted_at);

-- 操作日志表索引
CREATE INDEX idx_operation_logs_user_id ON operation_logs(user_id);
CREATE INDEX idx_operation_logs_operation_type ON operation_logs(operation_type);
CREATE INDEX idx_operation_logs_target ON operation_logs(operation_target, target_id);
CREATE INDEX idx_operation_logs_created_at ON operation_logs(created_at DESC);
CREATE INDEX idx_operation_logs_ip_address ON operation_logs(ip_address);

-- 创建空间索引（如果安装了PostGIS）
CREATE INDEX idx_gateways_location ON gateways USING GIST (ST_SetSRID(ST_MakePoint(longitude, latitude), 4326));
CREATE INDEX idx_devices_location ON devices USING GIST (ST_SetSRID(ST_MakePoint(longitude, latitude), 4326));
CREATE INDEX idx_sensor_tags_location ON sensor_tags USING GIST (ST_SetSRID(ST_MakePoint(longitude, latitude), 4326));

-- 创建触发器函数：更新时间戳
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- 为所有需要自动更新时间的表创建触发器
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_gateways_updated_at BEFORE UPDATE ON gateways FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_devices_updated_at BEFORE UPDATE ON devices FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_sensor_tags_updated_at BEFORE UPDATE ON sensor_tags FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_alarms_updated_at BEFORE UPDATE ON alarms FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- 创建视图：在线设备统计
CREATE VIEW device_online_status AS
SELECT 
    d.device_id,
    d.device_name,
    d.status,
    d.last_data_received,
    d.heartbeat_interval_seconds,
    CASE 
        WHEN d.last_data_received IS NULL THEN false
        WHEN CURRENT_TIMESTAMP - d.last_data_received > INTERVAL '1 second' * d.heartbeat_interval_seconds * 2 THEN false
        ELSE true
    END AS is_online,
    g.gateway_name,
    g.status as gateway_status
FROM devices d
JOIN gateways g ON d.gateway_id = g.gateway_id;

-- 创建视图：传感器统计数据
CREATE VIEW sensor_statistics AS
SELECT 
    st.sensor_tag_id,
    st.sensor_name,
    st.sensor_type,
    st.status,
    d.device_name,
    g.gateway_name,
    u.username as owner_name,
    st.installed_at,
    st.last_calibration_at,
    EXTRACT(DAY FROM CURRENT_TIMESTAMP - st.last_calibration_at) as days_since_calibration
FROM sensor_tags st
JOIN devices d ON st.device_id = d.device_id
JOIN gateways g ON d.gateway_id = g.gateway_id
JOIN users u ON g.owner_id = u.user_id;

-- 创建视图：报警摘要
CREATE VIEW alarm_summary AS
SELECT 
    a.alarm_id,
    a.alarm_type,
    a.alarm_level,
    a.alarm_status,
    a.title,
    a.trigger_time,
    a.acknowledge_time,
    a.resolved_time,
    CASE 
        WHEN a.alarm_status IN ('NEW', 'PENDING', 'INVESTIGATING') THEN 'ACTIVE'
        ELSE 'INACTIVE'
    END as active_status,
    COALESCE(st.sensor_name, d.device_name, g.gateway_name) as source_name,
    COALESCE(a.acknowledge_by, a.resolved_by) as handled_by,
    EXTRACT(EPOCH FROM COALESCE(a.resolved_time, a.acknowledge_time, CURRENT_TIMESTAMP) - a.trigger_time) as duration_seconds
FROM alarms a
LEFT JOIN sensor_tags st ON a.sensor_tag_id = st.sensor_tag_id
LEFT JOIN devices d ON a.device_id = d.device_id OR a.device_id = st.device_id
LEFT JOIN gateways g ON a.gateway_id = g.gateway_id OR g.gateway_id = d.gateway_id;

-- 插入初始数据（可选）
INSERT INTO users (username, password_hash, email, display_name, role, status, email_verified) 
VALUES 
    ('admin', crypt('admin123', gen_salt('bf')), 'admin@iot.com', '系统管理员', 'SUPER_ADMIN', 'ACTIVE', true),
    ('demo', crypt('demo123', gen_salt('bf')), 'demo@iot.com', '演示用户', 'USER', 'ACTIVE', true);

INSERT INTO gateways (gateway_sn, gateway_name, iccid, imei, owner_id, status, location) 
VALUES 
    ('GW-2026-001', '测试网关1', '89860123805500000001', '123456789012345', (SELECT user_id FROM users WHERE username = 'admin'), 'ACTIVE', '测试实验室'),
    ('GW-2026-002', '测试网关2', '89860123805500000002', '123456789012346', (SELECT user_id FROM users WHERE username = 'demo'), 'ACTIVE', '生产车间');

INSERT INTO devices (gateway_id, iccid, imei, device_name, device_type, status, location) 
VALUES 
    ((SELECT gateway_id FROM gateways WHERE gateway_sn = 'GW-2026-001'), 'DEV-2026-001', NULL, '温湿度传感器01', 'temperature_humidity_gateway', 'ACTIVE', '实验室南侧'),
    ((SELECT gateway_id FROM gateways WHERE gateway_sn = 'GW-2026-002'), 'DEV-2026-002', NULL, '温湿度传感器02', 'temperature_humidity_gateway', 'ACTIVE', '车间北侧');

INSERT INTO sensor_tags (device_id, sensor_sn, sensor_name, sensor_type, status, measurement_unit) 
VALUES 
    ((SELECT device_id FROM devices WHERE iccid = 'DEV-2026-001'), 'ST-2026-001', '温度传感器01', 'temperature', 'ACTIVE', '°C'),
    ((SELECT device_id FROM devices WHERE iccid = 'DEV-2026-001'), 'ST-2026-002', '湿度传感器01', 'humidity', 'ACTIVE', '%'),
    ((SELECT device_id FROM devices WHERE iccid = 'DEV-2026-002'), 'ST-2026-003', '温度传感器02', 'temperature', 'ACTIVE', '°C'),
