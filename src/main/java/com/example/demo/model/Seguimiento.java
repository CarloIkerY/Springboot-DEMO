package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Seguimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seguimiento_id;

    @ManyToOne
    @JoinColumn(name = "detalle_id", nullable = false)
    private CotizacionDetalle cotizacionDetalle;

    @ManyToOne
    @JoinColumn(name = "mecanico_id", nullable = false)
    private Mecanico mecanico;

    private String estado;
    private LocalDate fecha_actualizacion;
}
