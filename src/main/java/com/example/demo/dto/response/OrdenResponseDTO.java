package com.example.demo.dto.response;

import com.example.demo.dto.AutoDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrdenResponseDTO {
    private Long orden_id;
    private String numeroOrden;
    private LocalDate fechaCreacion;

    private AutoDTO auto;
    private SeguimientoDTO seguimiento;
}