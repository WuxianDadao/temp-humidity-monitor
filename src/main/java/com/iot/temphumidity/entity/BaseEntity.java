package com.iot.temphumidity.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 基础实体类，提供公共字段
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
    
    /**
     * 获取实体标识（用于日志和调试）
     */
    public String getEntityIdentifier() {
        return getClass().getSimpleName() + "#" + id;
    }
    
    /**
     * 检查是否为新建实体
     */
    public boolean isNew() {
        return id == null;
    }
    
    /**
     * 获取创建时间（显示格式）
     */
    public String getCreatedAtDisplay() {
        if (createdAt == null) return "未记录";
        return createdAt.toString();
    }
    
    /**
     * 获取更新时间（显示格式）
     */
    public String getUpdatedAtDisplay() {
        if (updatedAt == null) return "未记录";
        return updatedAt.toString();
    }
    
    /**
     * 创建前的回调方法
     */
    @PrePersist
    protected void onCreate() {
        // 子类可以重写此方法
    }
    
    /**
     * 更新前的回调方法
     */
    @PreUpdate
    protected void onUpdate() {
        // 子类可以重写此方法
    }
}