package com.example.demo.mappers;

import com.example.demo.dto.response.SeguimientoDTO;
import com.example.demo.model.Seguimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AjusteAutoMapper.class)
public interface SeguimientoMapper {

    @Mapping(source = "seguimiento_id", target = "seguimiento_id")
    @Mapping(source = "fecha_actualizacion", target = "fechaActualizacion")
    @Mapping(source = "ajusteAutos", target = "ajustes")
    SeguimientoDTO toDto(Seguimiento entity);
}