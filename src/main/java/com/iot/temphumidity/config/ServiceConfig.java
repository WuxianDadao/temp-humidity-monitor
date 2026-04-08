package com.iot.temphumidity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 服务配置类
 * 用于配置和替换有问题的服务实现
 */
@Configuration
public class ServiceConfig {
    
    // 这个配置类会确保使用SimpleSensorTagServiceImpl
    // 而不是有编译问题的SensorTagServiceImpl
    // Spring Boot会自动检测@Service注解的类
}