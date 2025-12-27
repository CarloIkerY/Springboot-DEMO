package com.example.demo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SeguimientoDTO {
    private Integer seguimiento_id;
    private EstadoDTO estado;
    private LocalDateTime fechaActualizacion;
    private Boolean ajustes_aceptados;

    private List<AjusteAutoDTO> ajustes;
}