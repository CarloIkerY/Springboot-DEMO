package com.example.demo.dto.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenInsumosRequestDTO {

    // "AGREGAR" o "QUITAR"
    private String accion;

    // proveedores a agregar/quitar
    private List<Integer> proveedoresIds;

    // piezas a agregar/quitar
    private List<PiezaLineaDTO> piezas;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PiezaLineaDTO {
        private Long piezaId;
        private Integer cantidad;      // requerido para AGREGAR
        private Double costo_unitario;
    }
}
