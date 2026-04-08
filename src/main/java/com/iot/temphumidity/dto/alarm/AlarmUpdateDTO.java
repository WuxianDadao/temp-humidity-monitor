package com.iot.temphumidity.dto.alarm;

import com.iot.temphumidity.enums.AlarmLevel;
import com.iot.temphumidity.enums.AlarmStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 报警更新DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmUpdateDTO {
    
    /**
     * 报警ID
     */
    private Long id;
    
    /**
     * 报警状态
     */
    private AlarmStatus status;
    
    /**
     * 报警级别
     */
    private AlarmLevel level;
    
    /**
     * 处理人员ID
     */
    private Long handlerId;
    
    /**
     * 处理人员姓名
     */
    private String handlerName;
    
    /**
     * 处理说明
     */
    private String handleDescription;
    
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    
    /**
     * 确认人员ID
     */
    private Long acknowledgeId;
    
    /**
     * 确认人员姓名
     */
    private String acknowledgeName;
    
    /**
     * 确认说明
     */
    private String acknowledgeDescription;
    
    /**
     * 确认时间
     */
    private LocalDateTime acknowledgeTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 是否已通知
     */
    private Boolean notified;
    
    /**
     * 通知方式
     */
    private String notificationMethod;
    
    /**
     * 通知时间
     */
    private LocalDateTime notificationTime;
    
    /**
     * 是否已升级
     */
    private Boolean escalated;
    
    /**
     * 升级说明
     */
    private String escalationDescription;
    
    /**
     * 升级时间
     */
    private LocalDateTime escalationTime;
    
    /**
     * 是否已关闭
     */
    private Boolean closed;
    
    /**
     * 关闭说明
     */
    private String closeDescription;
    
    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;
    
    /**
     * 预期解决时间
     */
    private LocalDateTime expectedResolveTime;
    
    /**
     * 实际解决时间
     */
    private LocalDateTime actualResolveTime;
    
    /**
     * 解决说明
     */
    private String resolveDescription;
    
    /**
     * 解决方案
     */
    private String resolution;
    
    /**
     * 相关文档链接
     */
    private String documentLink;
    
    /**
     * 相关工单ID
     */
    private String ticketId;
    
    /**
     * 是否已复盘
     */
    private Boolean reviewed;
    
    /**
     * 复盘说明
     */
    private String reviewDescription;
    
    /**
     * 复盘时间
     */
    private LocalDateTime reviewTime;
    
    /**
     * 根因分析
     */
    private String rootCause;
    
    /**
     * 预防措施
     */
    private String preventiveMeasure;
    
    /**
     * 是否已归档
     */
    private Boolean archived;
    
    /**
     * 归档时间
     */
    private LocalDateTime archiveTime;
    
    /**
     * 归档说明
     */
    private String archiveDescription;
    
    /**
     * 数据版本（用于乐观锁）
     */
    private Long version;
}