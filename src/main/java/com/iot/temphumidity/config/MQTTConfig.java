package com.iot.temphumidity.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * MQTT配置类
 * 配置MQTT客户端连接
 */
@Slf4j
@Configuration
public class MQTTConfig {

    @Value("${mqtt.broker.url:tcp://localhost:1883}")
    private String brokerUrl;

    @Value("${mqtt.client.id:iot-temp-humidity-service}")
    private String clientId;

    @Value("${mqtt.username:}")
    private String username;

    @Value("${mqtt.password:}")
    private String password;

    @Value("${mqtt.connection.timeout:10}")
    private int connectionTimeout;

    @Value("${mqtt.keepalive.interval:60}")
    private int keepAliveInterval;

    @Value("${mqtt.clean.session:true}")
    private boolean cleanSession;

    @Value("${mqtt.automatic.reconnect:true}")
    private boolean automaticReconnect;

    @Value("${mqtt.max.reconnect.delay:128}")
    private int maxReconnectDelay;

    @Value("${mqtt.qos:1}")
    private int qos;

    /**
     * MQTT连接选项配置
     */
    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        
        // 设置连接参数
        options.setCleanSession(cleanSession);
        options.setConnectionTimeout(connectionTimeout);
        options.setKeepAliveInterval(keepAliveInterval);
        options.setAutomaticReconnect(automaticReconnect);
        options.setMaxReconnectDelay(maxReconnectDelay);
        
        // 设置认证信息
        if (username != null && !username.isEmpty()) {
            options.setUserName(username);
        }
        if (password != null && !password.isEmpty()) {
            options.setPassword(password.toCharArray());
        }
        
        // 设置遗嘱消息
        try {
            String willPayload = "{\"status\": \"offline\", \"clientId\": \"" + clientId + "\"}";
            options.setWill("device/status/" + clientId, willPayload.getBytes(), qos, true);
        } catch (Exception e) {
            log.warn("Failed to set MQTT will message: {}", e.getMessage());
        }
        
        return options;
    }

    /**
     * MQTT客户端配置
     */
    @Bean
    public MqttClient mqttClient(MqttConnectOptions mqttConnectOptions) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
        
        try {
            MqttClient client = new MqttClient(brokerUrl, clientId, persistence);
            client.connect(mqttConnectOptions);
            
            // 连接成功后发送在线状态
            if (client.isConnected()) {
                try {
                    String onlinePayload = "{\"status\": \"online\", \"clientId\": \"" + clientId + "\", \"timestamp\": \"" + 
                             System.currentTimeMillis() + "\"}";
                    client.publish("device/status/" + clientId, onlinePayload.getBytes(), qos, false);
                } catch (Exception e) {
                    log.warn("Failed to publish online status: {}", e.getMessage());
                }
            }
            
            return client;
        } catch (MqttException e) {
            throw new RuntimeException("Failed to create MQTT client: " + e.getMessage(), e);
        }
    }

    /**
     * 数据接收主题配置
     */
    @Bean(name = "dataReceiveTopic")
    public String dataReceiveTopic() {
        return "sensor/data/#";
    }

    /**
     * 设备状态主题配置
     */
    @Bean(name = "deviceStatusTopic")
    public String deviceStatusTopic() {
        return "device/status/#";
    }

    /**
     * 设备配置主题配置
     */
    @Bean(name = "deviceConfigTopic")
    public String deviceConfigTopic() {
        return "device/config/#";
    }

    /**
     * 报警通知主题配置
     */
    @Bean(name = "alarmNotificationTopic")
    public String alarmNotificationTopic() {
        return "alarm/notification/#";
    }

    /**
     * 服务质量(QoS)配置
     */
    @Bean(name = "mqttQos")
    public int mqttQos() {
        return qos;
    }
}