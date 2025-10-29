package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutoDTO {
    private String marca;
    private String modelo;
    private Integer anio;
    private String placa;
    private String color;
    private String numero_serie;
    private Boolean transmision;
    private Integer kilometraje;
}