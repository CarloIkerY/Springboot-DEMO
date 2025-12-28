package com.example.demo.mappers;

import com.example.demo.dto.request.AjusteAutoUpdateRequestDTO;
import com.example.demo.dto.response.AjusteAutoDTO;
import com.example.demo.dto.response.AjusteAutoResponseDTO;
import com.example.demo.model.Ajuste_auto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { SubajusteMapper.class })
public interface AjusteAutoMapper {

    @Mapping(source = "ajusteAuto_id", target = "ajusteAuto_id")
    @Mapping(source = "fecha_creacion", target = "fechaCreacion")
    @Mapping(source = "subajustes", target = "subajustes")

    // Ajuste
    @Mapping(source = "ajuste.ajuste_id", target = "ajuste_id")
    @Mapping(source = "ajuste.descripcion", target = "ajusteDescripcion")

    // Seguimiento / Estado
    @Mapping(source = "seguimiento.seguimiento_id", target = "seguimiento_id")
    @Mapping(source = "seguimiento.estado.estado_id", target = "estadoId")
    @Mapping(source = "seguimiento.estado.estado", target = "estado")
    AjusteAutoResponseDTO toDto(Ajuste_auto entity);

    @Mapping(target = "ajusteAuto_id", ignore = true)
    @Mapping(target = "seguimiento", ignore = true)
    @Mapping(target = "ajuste", ignore = true)
    @Mapping(target = "fecha_creacion", ignore = true)
    void updateEntityFromDto(
            AjusteAutoUpdateRequestDTO dto,
            @MappingTarget Ajuste_auto entity
    );
}
