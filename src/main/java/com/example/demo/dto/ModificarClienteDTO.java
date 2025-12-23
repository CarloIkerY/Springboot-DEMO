package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificarClienteDTO {

    private String nombre;
    private String celular;
    private String direccion;
    private Boolean clienteUNAM;
}
