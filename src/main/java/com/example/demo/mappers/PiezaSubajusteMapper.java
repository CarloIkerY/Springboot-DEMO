package com.example.demo.mappers;

import com.example.demo.dto.ProveedorDTO;
import com.example.demo.dto.response.PiezaSubajusteResponseDTO;
import com.example.demo.model.Pieza_subajuste;
import com.example.demo.model.Proveedor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        uses = { OrdenMapper.class, SubajusteMapper.class }
)
public interface PiezaSubajusteMapper {

    @Mapping(source = "piezaSubajuste_id", target = "piezaSubajusteId")
    @Mapping(source = "fecha_solicitud", target = "fechaSolicitud")
    @Mapping(source = "fecha_recibido", target = "fechaRecibido")
    @Mapping(source = "costo_unitario_compra", target = "costoUnitarioCompra")

    @Mapping(source = "subajuste.subajuste_id", target = "subajusteId")
    @Mapping(source = "pieza.pieza_id", target = "piezaId")
    @Mapping(source = "proveedor.proveedor_id", target = "proveedorId")


    @Mapping(source = "subajuste", target = "subajuste")
    @Mapping(source = "subajuste.ajusteAuto.seguimiento.orden", target = "orden")


    @Mapping(source = "proveedor", target = "proveedor", qualifiedByName = "toProveedorDTO")


    @Mapping(source = "pieza.nombre", target = "piezaNombre")
    @Mapping(source = "pieza.costo_unitario", target = "piezaCostoCatalogo")
    PiezaSubajusteResponseDTO toDto(Pieza_subajuste entity);

    @Named("toProveedorDTO")
    default ProveedorDTO toProveedorDTO(Proveedor p) {
        if (p == null) return null;
        return ProveedorDTO.builder()
                .accion(null)
                .proveedor_id(p.getProveedor_id())
                .nombre(p.getNombre())
                .telefono(p.getTelefono())
                .correo(p.getCorreo())
                .build();
    }
}
