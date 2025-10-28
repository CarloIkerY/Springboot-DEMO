package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Ajuste")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ajuste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ajuste_id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double costo_estimado;

    @OneToMany(mappedBy = "ajuste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PiezaAjuste> piezaAjuste;

    @OneToMany(mappedBy = "ajuste", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CotizacionDetalle> cotizacionDetalle;
}
