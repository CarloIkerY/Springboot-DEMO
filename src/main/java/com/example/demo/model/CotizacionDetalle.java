package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Cotizacion_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CotizacionDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detalle_id;

    @ManyToOne
    @JoinColumn(name = "cotizacion_id", nullable = false)
    private Cotizacion cotizacion;

    @ManyToOne
    @JoinColumn(name = "ajuste_id", nullable = false)
    private Ajuste ajuste;

    @Column(nullable = false)
    private Double costo;

    @Column(nullable = false)
    private Boolean aceptado;

    @Column(nullable = false)
    private Boolean enviado_por_mecanico;

    @OneToMany(mappedBy = "cotizacionDetalle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seguimiento> seguimiento;
}
