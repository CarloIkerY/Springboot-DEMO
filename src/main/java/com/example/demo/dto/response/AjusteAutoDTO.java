package com.example.demo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AjusteAutoDTO {
    private Long ajusteAuto_id;
    private String descripcion;
    private LocalDateTime fechaCreacion;

    private AjusteSimpleDTO ajuste;
}
