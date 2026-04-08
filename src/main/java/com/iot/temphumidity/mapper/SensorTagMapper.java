package com.iot.temphumidity.mapper;

import com.iot.temphumidity.entity.SensorTag;
import com.iot.temphumidity.dto.sensor.SensorTagDTO;
import com.iot.temphumidity.dto.sensor.SensorTagCreateDTO;
import com.iot.temphumidity.dto.sensor.SensorTagUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SensorTag实体与DTO之间的映射器
 * 使用MapStruct进行对象转换
 */
@Mapper(componentModel = "spring")
public interface SensorTagMapper {
    
    SensorTagMapper INSTANCE = Mappers.getMapper(SensorTagMapper.class);
    
    /**
     * 将SensorTag实体转换为DTO
     */
    SensorTagDTO toDTO(SensorTag sensorTag);
    
    /**
     * 将DTO转换为SensorTag实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SensorTag toEntity(SensorTagCreateDTO dto);
    
    /**
     * 更新实体从DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(SensorTagUpdateDTO updateDTO, @MappingTarget SensorTag entity);
    
    /**
     * 将实体列表转换为DTO列表
     */
    List<SensorTagDTO> toDTOList(List<SensorTag> sensorTags);
    
    /**
     * 将DTO列表转换为实体列表
     */
    List<SensorTag> toEntityList(List<SensorTagCreateDTO> dtos);
}
