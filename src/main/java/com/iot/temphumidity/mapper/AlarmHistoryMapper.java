package com.iot.temphumidity.mapper;

import com.iot.temphumidity.dto.alarm.AlarmHistoryDTO;
import com.iot.temphumidity.entity.postgresql.AlarmHistoryEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 报警历史数据转换器
 */
@Mapper(componentModel = "spring", 
        uses = {CommonMapper.class, DateTimeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlarmHistoryMapper {
    
    AlarmHistoryMapper INSTANCE = Mappers.getMapper(AlarmHistoryMapper.class);
    
    /**
     * 实体转DTO
     */
    @Mapping(target = "durationSeconds", source = "calculatedDuration")
    @Mapping(target = "durationHumanReadable", ignore = true)
    @Mapping(target = "sourceInfo", ignore = true)
    @Mapping(target = "alarmDetails", ignore = true)
    AlarmHistoryDTO toDTO(AlarmHistoryEntity entity);
    
    /**
     * DTO转实体
     */
    @Mapping(target = "calculatedDuration", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AlarmHistoryEntity toEntity(AlarmHistoryDTO dto);
    
    /**
     * 更新实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(AlarmHistoryDTO dto, @MappingTarget AlarmHistoryEntity entity);
    
    /**
     * 自定义转换：计算持续时间
     */
    @AfterMapping
    default void calculateDuration(@MappingTarget AlarmHistoryDTO dto, AlarmHistoryEntity entity) {
        if (entity != null) {
            // 计算持续时间
            Long duration = entity.calculateDuration();
            dto.setDurationSeconds(duration);
            
            // 生成人类可读格式
            if (duration != null) {
                long hours = duration / 3600;
                long minutes = (duration % 3600) / 60;
                long seconds = duration % 60;
                
                if (hours > 0) {
                    dto.setDurationHumanReadable(String.format("%dh %dm %ds", hours, minutes, seconds));
                } else if (minutes > 0) {
                    dto.setDurationHumanReadable(String.format("%dm %ds", minutes, seconds));
                } else {
                    dto.setDurationHumanReadable(String.format("%ds", seconds));
                }
            }
            
            // 设置源信息
            dto.setSourceInfo(entity.getSourceInfo());
            
            // 设置报警详情
            dto.setAlarmDetails(entity.getAlarmDetails());
        }
    }
    
    /**
     * 自定义转换：计算状态
     */
    @AfterMapping
    default void calculateStatus(@MappingTarget AlarmHistoryEntity entity) {
        if (entity.getStatus() == null) {
            entity.setStatus(entity.calculateStatus());
        }
    }
}