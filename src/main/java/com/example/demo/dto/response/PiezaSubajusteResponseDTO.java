package com.example.demo.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class PiezaSubajusteResponseDTO {
    private Long piezaSubajusteId;
    private Long ordenId;
    private Long subajusteId;
    private Long piezaId;
    private Integer proveedorId;
    private Integer cantidad;
    private Double costoUnitarioCompra;
    private Double subtotal;
    private Date fechaSolicitud;
    private Date fechaRecibido;
}
