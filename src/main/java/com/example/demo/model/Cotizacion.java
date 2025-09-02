package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Cotizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cotizacion_id;

    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

    private LocalDate fecha;
    private Double costo_total;
    private String estatus;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CotizacionDetalle> cotizacionesDetalle;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evidencia> evidencia;
}