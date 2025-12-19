package com.example.demo.dto;

import lombok.Data;

@Data
public class PiezaAccionDTO {

    // "AGREGAR" | "ELIMINAR"
    private String accion;

    // Para ELIMINAR
    private Integer pieza_id;

    // Para AGREGAR
    private String nombre;
    private Double costo_unitario;

}