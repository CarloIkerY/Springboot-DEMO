package com.example.demo.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PiezaSubajusteResponseDTO {
    private Integer piezaSubajusteId;
    private Long ordenId;
    private Long subajusteId;
    private Integer proveedorId;
    private Integer piezaId;
    private Integer cantidad;
    private Double costoUnitarioCompra;
    private Double subtotal;
    private Date fechaSolicitud;
    private Date fechaRecibido;
}
