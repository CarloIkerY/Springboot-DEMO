package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenDTO {
    private Long orden_id;
    private Long auto_id;
    private Long usuario_id;
    private Long estado;
}
