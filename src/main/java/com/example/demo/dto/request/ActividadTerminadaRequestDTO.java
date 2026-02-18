package com.example.demo.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ActividadTerminadaRequestDTO {
    private Long orden_id;
    private Long ajusteAuto_id;
    private Long subajuste_id;
    private List<Long> actividadesId;
}
