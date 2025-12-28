package com.example.demo.mappers;

import com.example.demo.dto.response.OrdenResponseDTO;
import com.example.demo.model.Orden;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SeguimientoMapper.class)
public interface OrdenMapper {
    @Mapping(source = "orden_id", target = "orden_id")
    @Mapping(source = "numero_orden", target = "numeroOrden")
    @Mapping(source = "fecha_creacion", target = "fechaCreacion")
    @Mapping(source = "seguimiento", target = "seguimiento")
    OrdenResponseDTO toDto(Orden orden);
}