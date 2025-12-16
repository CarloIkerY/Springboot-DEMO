package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDTO {
    private Long empresa_id;
    private String nombreEmpresa;
    private String dependenciaEmpresa;
    private String telefonoOficinaEmpresa;
}