package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CondicionAutoDTO {
    private Long auto_id;
    private String descripcion;
    private String observaciones;
    private Integer kilometraje;
    private Map<String, Object> detalles;
}
