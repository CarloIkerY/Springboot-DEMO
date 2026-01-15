package com.example.demo.dto.request;

import com.example.demo.dto.response.ActividadDTO;
import lombok.Data;

import java.util.List;

@Data
public class ActividadRequestDTO {
    private Long orden_id;
    private Long ajusteAuto_id;
    private Long subajuste_id;
    private List<ActividadDTO> actividades;
}
