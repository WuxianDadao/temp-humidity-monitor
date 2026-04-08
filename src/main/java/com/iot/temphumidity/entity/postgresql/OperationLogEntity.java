package com.iot.temphumidity.entity.postgresql;

import com.iot.temphumidity.entity.BaseEntity;
import com.iot.temphumidity.enums.OperationResult;
import com.iot.temphumidity.enums.OperationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 操作日志实体类 - 记录系统操作历史
 */
@Entity
@Table(name = "operation_log", schema = "iot", indexes = {
    @Index(name = "idx_operation_user_id", columnList = "user_id"),
    @Index(name = "idx_operation_type", columnList = "operation_type"),
    @Index(name = "idx_operation_time", columnList = "operation_time"),
    @Index(name = "idx_operation_ip", columnList = "client_ip"),
    @Index(name = "idx_operation_module", columnList = "module_name")
})
@Getter
@Setter
public class OperationLogEntity extends BaseEntity {
    
    /**
     * 操作用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    
    /**
     * 操作类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false, length = 50)
    private OperationType operationType;
    
    /**
     * 操作结果
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_result", nullable = false, length = 20)
    private OperationResult operationResult = OperationResult.SUCCESS;
    
    /**
     * 操作时间
     */
    @Column(name = "operation_time", nullable = false)
    private LocalDateTime operationTime;
    
    /**
     * 模块名称
     */
    @Column(name = "module_name", nullable = false, length = 100)
    private String moduleName;
    
    /**
     * 操作内容
     */
    @Column(name = "operation_content", nullable = false, length = 2000)
    private String operationContent;
    
    /**
     * 操作详情
     */
    @Column(name = "operation_details", length = 4000)
    private String operationDetails;
    
    /**
     * 请求方法
     */
    @Column(name = "request_method", length = 10)
    private String requestMethod;
    
    /**
     * 请求URL
     */
    @Column(name = "request_url", length = 500)
    private String requestUrl;
    
    /**
     * 请求参数
     */
    @Column(name = "request_params", length = 2000)
    private String requestParams;
    
    /**
     * 响应状态码
     */
    @Column(name = "response_status")
    private Integer responseStatus;
    
    /**
     * 客户端IP
     */
    @Column(name = "client_ip", length = 50)
    private String clientIp;
    
    /**
     * 客户端标识
     */
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    
    /**
     * 执行时间（毫秒）
     */
    @Column(name = "execution_time")
    private Long executionTime;
    
    /**
     * 错误消息
     */
    @Column(name = "error_message", length = 2000)
    private String errorMessage;
    
    /**
     * 错误堆栈
     */
    @Column(name = "error_stack", columnDefinition = "TEXT")
    private String errorStack;
    
    /**
     * 关联实体类型
     */
    @Column(name = "entity_type", length = 100)
    private String entityType;
    
    /**
     * 关联实体ID
     */
    @Column(name = "entity_id")
    private Long entityId;
    
    /**
     * 关联的业务ID（如设备SN、网关ICCID等）
     */
    @Column(name = "business_id", length = 100)
    private String businessId;
    
    /**
     * 关联的用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;
    
    /**
     * 默认构造函数
     */
    public OperationLogEntity() {
        super();
        this.operationTime = LocalDateTime.now();
    }
    
    /**
     * 创建成功日志
     */
    public static OperationLogEntity createSuccessLog(Long userId, OperationType operationType, 
            String moduleName, String operationContent) {
        OperationLogEntity log = new OperationLogEntity();
        log.userId = userId;
        log.operationType = operationType;
        log.operationResult = OperationResult.SUCCESS;
        log.moduleName = moduleName;
        log.operationContent = operationContent;
        log.operationTime = LocalDateTime.now();
        return log;
    }
    
    /**
     * 创建失败日志
     */
    public static OperationLogEntity createFailureLog(Long userId, OperationType operationType,
            String moduleName, String operationContent, String errorMessage) {
        OperationLogEntity log = new OperationLogEntity();
        log.userId = userId;
        log.operationType = operationType;
        log.operationResult = OperationResult.FAILURE;
        log.moduleName = moduleName;
        log.operationContent = operationContent;
        log.errorMessage = errorMessage;
        log.operationTime = LocalDateTime.now();
        return log;
    }
    
    /**
     * 创建设备操作日志
     */
    public static OperationLogEntity createDeviceLog(Long userId, OperationType operationType,
            String deviceSn, String operationContent, OperationResult result) {
        OperationLogEntity log = new OperationLogEntity();
        log.userId = userId;
        log.operationType = operationType;
        log.operationResult = result;
        log.moduleName = "设备管理";
        log.operationContent = operationContent;
        log.businessId = deviceSn;
        log.operationTime = LocalDateTime.now();
        return log;
    }
    
    /**
     * 创建网关操作日志
     */
    public static OperationLogEntity createGatewayLog(Long userId, OperationType operationType,
            String gatewayIccid, String operationContent, OperationResult result) {
        OperationLogEntity log = new OperationLogEntity();
        log.userId = userId;
        log.operationType = operationType;
        log.operationResult = result;
        log.moduleName = "网关管理";
        log.operationContent = operationContent;
        log.businessId = gatewayIccid;
        log.operationTime = LocalDateTime.now();
        return log;
    }
    
    /**
     * 创建传感器操作日志
     */
    public static OperationLogEntity createSensorLog(Long userId, OperationType operationType,
            String sensorSn, String operationContent, OperationResult result) {
        OperationLogEntity log = new OperationLogEntity();
        log.userId = userId;
        log.operationType = operationType;
        log.operationResult = result;
        log.moduleName = "传感器管理";
        log.operationContent = operationContent;
        log.businessId = sensorSn;
        log.operationTime = LocalDateTime.now();
        return log;
    }
    
    /**
     * 设置请求信息
     */
    public void setRequestInfo(String requestMethod, String requestUrl, String requestParams) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestParams = requestParams;
    }
    
    /**
     * 设置客户端信息
     */
    public void setClientInfo(String clientIp, String userAgent) {
        this.clientIp = clientIp;
        this.userAgent = userAgent;
    }
    
    /**
     * 设置响应信息
     */
    public void setResponseInfo(Integer responseStatus, Long executionTime) {
        this.responseStatus = responseStatus;
        this.executionTime = executionTime;
    }
    
    /**
     * 设置错误信息
     */
    public void setErrorInfo(String errorMessage, String errorStack) {
        this.errorMessage = errorMessage;
        this.errorStack = errorStack;
        this.operationResult = OperationResult.FAILURE;
    }
    
    /**
     * 设置实体关联信息
     */
    public void setEntityInfo(String entityType, Long entityId, String businessId) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.businessId = businessId;
    }
    
    /**
     * 检查操作是否成功
     */
    public boolean isSuccess() {
        return OperationResult.SUCCESS.equals(this.operationResult);
    }
    
    /**
     * 检查操作是否失败
     */
    public boolean isFailure() {
        return OperationResult.FAILURE.equals(this.operationResult);
    }
    
    /**
     * 获取操作摘要
     */
    public String getOperationSummary() {
        return String.format("%s - %s [%s]", 
                moduleName,
                operationContent,
                operationResult.getDisplayName());
    }
    
    /**
     * 获取完整日志信息
     */
    public String getFullLogInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("操作日志:\n");
        if (user != null) {
            sb.append("用户: ").append(user.getDisplayName()).append(" (").append(user.getUsername()).append(")\n");
        }
        sb.append("时间: ").append(operationTime).append("\n");
        sb.append("模块: ").append(moduleName).append("\n");
        sb.append("类型: ").append(operationType.getDisplayName()).append("\n");
        sb.append("内容: ").append(operationContent).append("\n");
        sb.append("结果: ").append(operationResult.getDisplayName()).append("\n");
        
        if (requestMethod != null || requestUrl != null) {
            sb.append("请求: ").append(requestMethod).append(" ").append(requestUrl).append("\n");
        }
        if (clientIp != null) {
            sb.append("客户端: ").append(clientIp).append("\n");
        }
        if (executionTime != null) {
            sb.append("耗时: ").append(executionTime).append("ms\n");
        }
        if (errorMessage != null) {
            sb.append("错误: ").append(errorMessage).append("\n");
        }
        
        return sb.toString();
    }
}