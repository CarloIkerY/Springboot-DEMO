package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaClienteDTO {
    private Long empresaCliente_id;
    private EmpresaDTO empresa;
    private String dependencia;
    private String telefonoOficina;
}
