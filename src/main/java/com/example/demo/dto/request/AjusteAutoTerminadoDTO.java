package com.example.demo.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AjusteAutoTerminadoDTO {
    private Long orden_id;
    private List<Long> ajustesAuto_id;
}
