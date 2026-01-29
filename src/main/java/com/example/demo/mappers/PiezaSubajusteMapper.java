package com.example.demo.mappers;

import com.example.demo.dto.PiezaSubajusteResponseDTO;
import com.example.demo.model.Pieza_subajuste;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PiezaSubajusteMapper {

    @Mapping(target = "piezaSubajusteId", source = "piezaSubajuste_id")
    @Mapping(target = "subajusteId", source = "subajuste.subajuste_id")
    @Mapping(target = "proveedorId", source = "proveedor.proveedor_id")
    @Mapping(target = "piezaId", source = "pieza.pieza_id")
    @Mapping(target = "costoUnitarioCompra", source = "costo_unitario_compra")
    @Mapping(target = "fechaSolicitud", source = "fecha_solicitud")
    @Mapping(target = "fechaRecibido", source = "fecha_recibido")
    PiezaSubajusteResponseDTO toDto(Pieza_subajuste entity);
}

