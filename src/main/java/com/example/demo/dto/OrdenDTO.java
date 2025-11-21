package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenDTO {
    private Long usuario_id;
    private Long auto_id;
    private Long orden_id;
    private Long estado;
    private LocalDate fecha_recoleccion;
    private LocalDate fecha_entrega;
}
