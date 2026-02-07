package com.example.demo.mappers;

import com.example.demo.dto.response.PiezaSubajusteResponseDTO;
import com.example.demo.model.Pieza_subajuste;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PiezaSubajusteMapper {

    @Mapping(source = "piezaSubajuste_id", target = "piezaSubajusteId")
    @Mapping(source = "subajuste.subajuste_id", target = "subajusteId")
    @Mapping(source = "subajuste.ajusteAuto.seguimiento.orden.orden_id", target = "ordenId")

    @Mapping(source = "pieza.pieza_id", target = "piezaId")                 // long -> Long OK
    @Mapping(source = "proveedor.proveedor_id", target = "proveedorId")     // Integer -> Integer OK

    @Mapping(source = "costo_unitario_compra", target = "costoUnitarioCompra")
    @Mapping(source = "fecha_solicitud", target = "fechaSolicitud")
    @Mapping(source = "fecha_recibido", target = "fechaRecibido")
    PiezaSubajusteResponseDTO toDto(Pieza_subajuste entity);
}
