package com.iot.temphumidity.enums;

import lombok.Getter;

/**
 * 操作结果枚举
 */
@Getter
public enum OperationResult {
    
    /**
     * 成功
     */
    SUCCESS("成功", "success", "✅"),
    
    /**
     * 失败
     */
    FAILURE("失败", "failure", "❌"),
    
    /**
     * 部分成功
     */
    PARTIAL_SUCCESS("部分成功", "partial_success", "⚠️"),
    
    /**
     * 取消
     */
    CANCELLED("取消", "cancelled", "⏹️"),
    
    /**
     * 超时
     */
    TIMEOUT("超时", "timeout", "⏰"),
    
    /**
     * 拒绝访问
     */
    ACCESS_DENIED("拒绝访问", "access_denied", "🚫"),
    
    /**
     * 资源不存在
     */
    RESOURCE_NOT_FOUND("资源不存在", "resource_not_found", "🔍"),
    
    /**
     * 资源已存在
     */
    RESOURCE_EXISTS("资源已存在", "resource_exists", "🔒"),
    
    /**
     * 参数错误
     */
    INVALID_PARAMETER("参数错误", "invalid_parameter", "📝"),
    
    /**
     * 系统错误
     */
    SYSTEM_ERROR("系统错误", "system_error", "💥"),
    
    /**
     * 网络错误
     */
    NETWORK_ERROR("网络错误", "network_error", "📡"),
    
    /**
     * 数据库错误
     */
    DATABASE_ERROR("数据库错误", "database_error", "🗃️"),
    
    /**
     * 文件系统错误
     */
    FILE_SYSTEM_ERROR("文件系统错误", "file_system_error", "📁"),
    
    /**
     * 外部服务错误
     */
    EXTERNAL_SERVICE_ERROR("外部服务错误", "external_service_error", "🌐"),
    
    /**
     * 配置错误
     */
    CONFIG_ERROR("配置错误", "config_error", "⚙️"),
    
    /**
     * 版本不兼容
     */
    VERSION_MISMATCH("版本不兼容", "version_mismatch", "🔄"),
    
    /**
     * 存储空间不足
     */
    INSUFFICIENT_STORAGE("存储空间不足", "insufficient_storage", "💾"),
    
    /**
     * 内存不足
     */
    INSUFFICIENT_MEMORY("内存不足", "insufficient_memory", "🧠"),
    
    /**
     * 并发冲突
     */
    CONCURRENCY_CONFLICT("并发冲突", "concurrency_conflict", "⚔️"),
    
    /**
     * 依赖服务不可用
     */
    DEPENDENCY_UNAVAILABLE("依赖服务不可用", "dependency_unavailable", "🔗"),
    
    /**
     * 许可证无效
     */
    LICENSE_INVALID("许可证无效", "license_invalid", "📜"),
    
    /**
     * 操作被禁止
     */
    OPERATION_FORBIDDEN("操作被禁止", "operation_forbidden", "⛔"),
    
    /**
     * 操作正在进行中
     */
    OPERATION_IN_PROGRESS("操作正在进行中", "operation_in_progress", "⏳"),
    
    /**
     * 操作已排队
     */
    OPERATION_QUEUED("操作已排队", "operation_queued", "📋"),
    
    /**
     * 操作已暂停
     */
    OPERATION_PAUSED("操作已暂停", "operation_paused", "⏸️"),
    
    /**
     * 操作已停止
     */
    OPERATION_STOPPED("操作已停止", "operation_stopped", "⏹️"),
    
    /**
     * 操作已超时
     */
    OPERATION_TIMEOUT("操作已超时", "operation_timeout", "⏰"),
    
    /**
     * 操作已取消
     */
    OPERATION_CANCELLED("操作已取消", "operation_cancelled", "❌"),
    
    /**
     * 操作已验证
     */
    OPERATION_VALIDATED("操作已验证", "operation_validated", "✅"),
    
    /**
     * 操作已回滚
     */
    OPERATION_ROLLED_BACK("操作已回滚", "operation_rolled_back", "↩️"),
    
    /**
     * 操作已提交
     */
    OPERATION_COMMITTED("操作已提交", "operation_committed", "✅"),
    
    /**
     * 操作已完成
     */
    OPERATION_COMPLETED("操作已完成", "operation_completed", "🏁"),
    
    /**
     * 操作已跳过
     */
    OPERATION_SKIPPED("操作已跳过", "operation_skipped", "⏭️"),
    
    /**
     * 操作已忽略
     */
    OPERATION_IGNORED("操作已忽略", "operation_ignored", "👁️‍🗨️"),
    
    /**
     * 操作已警告
     */
    OPERATION_WARNED("操作已警告", "operation_warned", "⚠️"),
    
    /**
     * 操作已通知
     */
    OPERATION_NOTIFIED("操作已通知", "operation_notified", "📢"),
    
    /**
     * 操作已记录
     */
    OPERATION_LOGGED("操作已记录", "operation_logged", "📝"),
    
    /**
     * 操作已审计
     */
    OPERATION_AUDITED("操作已审计", "operation_audited", "🔍"),
    
    /**
     * 操作已备份
     */
    OPERATION_BACKED_UP("操作已备份", "operation_backed_up", "💾"),
    
    /**
     * 操作已恢复
     */
    OPERATION_RESTORED("操作已恢复", "operation_restored", "🔄"),
    
    /**
     * 操作已归档
     */
    OPERATION_ARCHIVED("操作已归档", "operation_archived", "🗄️"),
    
    /**
     * 操作已清理
     */
    OPERATION_CLEANED("操作已清理", "operation_cleaned", "🧹");
    
    private final String displayName;
    private final String code;
    private final String emoji;
    
    OperationResult(String displayName, String code, String emoji) {
        this.displayName = displayName;
        this.code = code;
        this.emoji = emoji;
    }
    
    /**
     * 根据代码获取枚举
     */
    public static OperationResult fromCode(String code) {
        for (OperationResult result : values()) {
            if (result.code.equalsIgnoreCase(code)) {
                return result;
            }
        }
        return SUCCESS;
    }
    
    /**
     * 检查是否成功
     */
    public boolean isSuccess() {
        return this == SUCCESS || 
               this == PARTIAL_SUCCESS ||
               this == OPERATION_COMPLETED ||
               this == OPERATION_COMMITTED ||
               this == OPERATION_VALIDATED;
    }
    
    /**
     * 检查是否失败
     */
    public boolean isFailure() {
        return this == FAILURE ||
               this == SYSTEM_ERROR ||
               this == NETWORK_ERROR ||
               this == DATABASE_ERROR ||
               this == FILE_SYSTEM_ERROR ||
               this == EXTERNAL_SERVICE_ERROR ||
               this == CONFIG_ERROR ||
               this == ACCESS_DENIED ||
               this == OPERATION_FORBIDDEN;
    }
    
    /**
     * 检查是否为错误类型
     */
    public boolean isError() {
        return name().endsWith("_ERROR") || name().contains("ERROR");
    }
    
    /**
     * 检查是否为警告类型
     */
    public boolean isWarning() {
        return this == PARTIAL_SUCCESS ||
               this == OPERATION_WARNED ||
               this == TIMEOUT ||
               this == VERSION_MISMATCH ||
               this == INSUFFICIENT_STORAGE ||
               this == INSUFFICIENT_MEMORY ||
               this == OPERATION_IN_PROGRESS ||
               this == OPERATION_QUEUED ||
               this == OPERATION_PAUSED;
    }
    
    /**
     * 检查是否为信息类型
     */
    public boolean isInfo() {
        return this == OPERATION_NOTIFIED ||
               this == OPERATION_LOGGED ||
               this == OPERATION_AUDITED ||
               this == OPERATION_BACKED_UP ||
               this == OPERATION_RESTORED ||
               this == OPERATION_ARCHIVED ||
               this == OPERATION_CLEANED ||
               this == OPERATION_SKIPPED ||
               this == OPERATION_IGNORED;
    }
    
    /**
     * 检查是否为操作状态
     */
    public boolean isOperationStatus() {
        return name().startsWith("OPERATION_");
    }
    
    /**
     * 获取对应的HTTP状态码
     */
    public int getHttpStatusCode() {
        switch (this) {
            case SUCCESS:
            case OPERATION_COMMITTED:
                return 200;
                
            case PARTIAL_SUCCESS:
                return 206;
                
            case OPERATION_COMPLETED:
                return 201;  // RESOURCE_CREATED 和 RESOURCE_NO_CONTENT 都映射到此
                
            case OPERATION_VALIDATED:
                return 202;  // RESOURCE_ACCEPTED 映射到此
                
            case INVALID_PARAMETER:
                return 400;
                
            case ACCESS_DENIED:
            case OPERATION_FORBIDDEN:
                return 403;
                
            case RESOURCE_NOT_FOUND:
                return 404;
                
            case RESOURCE_EXISTS:
                return 409;
                
            case CONCURRENCY_CONFLICT:
                return 409;
                
            case SYSTEM_ERROR:
            case DATABASE_ERROR:
            case FILE_SYSTEM_ERROR:
            case EXTERNAL_SERVICE_ERROR:
            case CONFIG_ERROR:
                return 500;
                
            case NETWORK_ERROR:
            case DEPENDENCY_UNAVAILABLE:
                return 503;
                
            case TIMEOUT:
            case OPERATION_TIMEOUT:
                return 504;
                
            default:
                return isSuccess() ? 200 : 500;
        }
    }
    
    /**
     * 获取对应的日志级别
     */
    public String getLogLevel() {
        if (isFailure() || isError()) {
            return "ERROR";
        } else if (isWarning()) {
            return "WARN";
        } else if (isInfo()) {
            return "INFO";
        } else if (isSuccess()) {
            return "INFO";
        } else {
            return "DEBUG";
        }
    }
    
    /**
     * 获取带表情的结果描述
     */
    public String getResultWithEmoji() {
        return emoji + " " + displayName;
    }
    
    /**
     * 获取简短结果描述
     */
    public String getShortDescription() {
        if (name().startsWith("OPERATION_")) {
            return name().substring("OPERATION_".length()).replace("_", " ");
        }
        return displayName;
    }
    
    /**
     * 获取所有成功结果
     */
    public static java.util.List<OperationResult> getAllSuccessResults() {
        java.util.List<OperationResult> results = new java.util.ArrayList<>();
        for (OperationResult result : values()) {
            if (result.isSuccess()) {
                results.add(result);
            }
        }
        return results;
    }
    
    /**
     * 获取所有失败结果
     */
    public static java.util.List<OperationResult> getAllFailureResults() {
        java.util.List<OperationResult> results = new java.util.ArrayList<>();
        for (OperationResult result : values()) {
            if (result.isFailure()) {
                results.add(result);
            }
        }
        return results;
    }
    
    /**
     * 获取所有错误结果
     */
    public static java.util.List<OperationResult> getAllErrorResults() {
        java.util.List<OperationResult> results = new java.util.ArrayList<>();
        for (OperationResult result : values()) {
            if (result.isError()) {
                results.add(result);
            }
        }
        return results;
    }
    
    /**
     * 获取结果类型
     */
    public String getResultType() {
        if (isSuccess()) return "success";
        if (isFailure()) return "failure";
        if (isWarning()) return "warning";
        if (isInfo()) return "info";
        return "unknown";
    }
}

// 添加一些缺失的常量
class OperationResultConstants {
    static final OperationResult RESOURCE_CREATED = OperationResult.OPERATION_COMPLETED;
    static final OperationResult RESOURCE_ACCEPTED = OperationResult.OPERATION_VALIDATED;
    static final OperationResult RESOURCE_NO_CONTENT = OperationResult.OPERATION_COMPLETED;
}