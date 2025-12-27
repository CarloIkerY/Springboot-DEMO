package com.example.demo.dto.request;

import lombok.Data;

@Data
public class AceptarAjustesRequestDTO {
    private Long orden_id;
    private Boolean ajustes_aceptados;
}