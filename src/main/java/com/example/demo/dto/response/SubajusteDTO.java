package com.example.demo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubajusteDTO {
    private Long subajuste_id;
    private String descripcion;
    private LocalDateTime fecha_registro;
    private List<ActividadDTO> actividades;
    private Boolean terminado;
}
