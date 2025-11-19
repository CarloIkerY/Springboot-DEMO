package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    private Long cliente_id;
    private String nombre;
    private String telefono;
    private String celular;
    private String correo;
    private String direccion;
    private Boolean clienteUNAM;
}
