package com.example.demo.dto.request;

import lombok.Data;

@Data
public class AjusteAutoUpdateRequestDTO {
    private Long orden_id;
    private Long ajusteAuto_id;
    private String descripcion;
}
