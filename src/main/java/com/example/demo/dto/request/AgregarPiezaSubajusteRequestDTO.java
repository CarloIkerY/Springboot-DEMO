package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AgregarPiezaSubajusteRequestDTO {

    @NotNull
    private Integer proveedorId;

    @NotNull
    private Long piezaId;

    @NotNull
    private Long ordenId;

    @NotNull
    private Long subajusteId;

    @NotNull @Min(1)
    private Integer cantidad;

    @NotNull @DecimalMin("0.01")
    private Double costoUnitario;
}
