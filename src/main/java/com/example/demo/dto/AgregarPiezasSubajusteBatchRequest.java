package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class AgregarPiezasSubajusteBatchRequest {
    private Long ordenId;
    private Long subajusteId;
    private List<Item> items;

    @Data
    public static class Item {
        private Integer proveedorId;
        private Integer piezaId;
        private Integer cantidad;
        private Double costoUnitario;
    }
}
