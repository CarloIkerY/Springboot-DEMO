package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioChoferDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String tipo;
    private boolean disponible;
    private VehiculoAsignado vehiculo_asignado;
}
