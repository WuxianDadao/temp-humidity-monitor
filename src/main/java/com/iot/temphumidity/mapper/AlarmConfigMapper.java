package com.iot.temphumidity.mapper;

import com.iot.temphumidity.dto.alarm.AlarmConfigDTO;
import com.iot.temphumidity.entity.postgresql.AlarmConfigEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 报警配置映射器
 */
@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AlarmConfigMapper {
    
    AlarmConfigMapper INSTANCE = Mappers.getMapper(AlarmConfigMapper.class);
    
    @Mapping(target = "customFields", ignore = true)
    AlarmConfigDTO toDTO(AlarmConfigEntity entity);
    
    AlarmConfigEntity toEntity(AlarmConfigDTO dto);
    
    void updateEntityFromDTO(AlarmConfigDTO dto, @MappingTarget AlarmConfigEntity entity);
    
    List<AlarmConfigDTO> toDTOList(List<AlarmConfigEntity> entities);
    
    List<AlarmConfigEntity> toEntityList(List<AlarmConfigDTO> dtos);
}