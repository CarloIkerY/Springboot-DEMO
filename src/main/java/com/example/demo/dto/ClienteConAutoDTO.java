package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteConAutoDTO {
    private String nombre;
    private String telefono;
    private String celular;
    private String correo;
    private String direccion;
    private Boolean clienteUNAM;
    private List<AutoDTO> autos;
}