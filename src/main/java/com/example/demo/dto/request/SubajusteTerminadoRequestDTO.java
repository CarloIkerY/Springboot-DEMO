package com.example.demo.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SubajusteTerminadoRequestDTO {
    private Long orden_id;
    private Long ajusteAuto_id;
    private List<Long> subajustes;
}
