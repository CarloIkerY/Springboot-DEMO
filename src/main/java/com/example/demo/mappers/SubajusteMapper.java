package com.example.demo.mappers;

import com.example.demo.dto.response.SubajusteDTO;
import com.example.demo.model.Subajuste;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ActividadMapper.class })
public interface SubajusteMapper {

    @Mapping(source = "subajuste_id", target = "subajuste_id")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "fecha_registro", target = "fecha_registro")
    @Mapping(source = "actividades", target = "actividades")
    SubajusteDTO toDto(Subajuste entity);
}