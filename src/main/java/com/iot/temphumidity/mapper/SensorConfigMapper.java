package com.iot.temphumidity.mapper;

import com.iot.temphumidity.dto.sensorconfig.SensorConfigCreateDTO;
import com.iot.temphumidity.dto.sensorconfig.SensorConfigDTO;
import com.iot.temphumidity.dto.sensorconfig.SensorConfigSyncDTO;
import com.iot.temphumidity.dto.sensorconfig.SensorConfigUpdateDTO;
import com.iot.temphumidity.entity.SensorConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 传感器配置Mapper
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SensorConfigMapper {
    
    SensorConfigMapper INSTANCE = Mappers.getMapper(SensorConfigMapper.class);
    
    /**
     * 将CreateDTO转换为实体
     */
    SensorConfig toEntity(SensorConfigCreateDTO createDTO);
    
    /**
     * 将实体转换为DTO
     */
    SensorConfigDTO toDTO(SensorConfig entity);
    
    /**
     * 将实体转换为SyncDTO
     */
    SensorConfigSyncDTO toSyncDTO(SensorConfig entity);
    
    /**
     * 使用UpdateDTO更新实体
     */
    void updateEntityFromUpdateDTO(SensorConfigUpdateDTO updateDTO, @MappingTarget SensorConfig entity);
    
    /**
     * 使用CreateDTO创建实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    @Mapping(target = "lastConfigSyncTime", ignore = true)
    @Mapping(target = "lastDataReceiveTime", ignore = true)
    @Mapping(target = "sensorTag", ignore = true)
    SensorConfig createEntityFromCreateDTO(SensorConfigCreateDTO createDTO);
}