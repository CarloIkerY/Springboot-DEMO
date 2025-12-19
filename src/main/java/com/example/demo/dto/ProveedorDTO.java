package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDTO {
    // "AGREGAR" | "ELIMINAR"
    private String accion;

    // Para ELIMINAR
    private Integer proveedor_id;

    // Para AGREGAR
    private String nombre;
    private String telefono;
    private String correo;
}
