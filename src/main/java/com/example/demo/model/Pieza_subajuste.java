package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "PiezaAjuste")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pieza_subajuste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer piezaSubajuste_id;

    @ManyToOne
    @JoinColumn(name = "subajuste_id", nullable = false)
    private Subajuste subajuste;

    @ManyToOne
    @JoinColumn(name = "pieza_id", nullable = false)
    private Pieza pieza;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Date fecha_solicitud;

    @Column(nullable = true)
    private Date fecha_recibido;
}