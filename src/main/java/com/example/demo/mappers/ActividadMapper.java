package com.example.demo.mappers;

import com.example.demo.dto.response.ActividadDTO;
import com.example.demo.model.Actividad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ActividadMapper {

    @Mapping(source = "actividad_id", target = "actividad_id")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "fecha_registro", target = "fecha_registro")
    @Mapping(source = "terminado", target = "terminado")
    ActividadDTO toDto(Actividad entity);

    @Mapping(target = "actividad_id", ignore = true)
    @Mapping(target = "subajuste", ignore = true)
    @Mapping(target = "fecha_registro", ignore = true)
    void updateEntityFromDto(@MappingTarget Actividad entity, ActividadDTO dto);
}