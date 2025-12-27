package com.example.demo.mappers;

import com.example.demo.dto.response.AjusteAutoDTO;
import com.example.demo.model.Ajuste_auto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AjusteAutoMapper {

    @Mapping(source = "ajuste.ajuste_id", target = "ajuste.ajuste_id")
    @Mapping(source = "ajuste.descripcion", target = "ajuste.descripcion")
    @Mapping(source = "fecha_creacion", target = "fechaCreacion")
    AjusteAutoDTO toDto(Ajuste_auto entity);
}
