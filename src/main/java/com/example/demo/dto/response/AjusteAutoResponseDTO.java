package com.example.demo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AjusteAutoResponseDTO {

    private Long ajusteAuto_id;
    private String descripcion;
    private LocalDateTime fechaCreacion;

    // Ajuste
    private Long ajuste_id;
    private String ajusteDescripcion;

    // Seguimiento / Estado
    private Integer seguimiento_id;
    private Long estadoId;
    private String estado;
}