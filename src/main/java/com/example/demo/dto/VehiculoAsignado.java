package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoAsignado {
    private Long id;
    private String marca;
    private String modelo;
    private String placas;
    private String capacidad;
}
