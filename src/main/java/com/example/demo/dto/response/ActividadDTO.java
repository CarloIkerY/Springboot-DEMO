package com.example.demo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActividadDTO {
    private Long actividad_id;
    private String descripcion;
    private LocalDateTime fecha_registro;
    private Boolean terminado;
}