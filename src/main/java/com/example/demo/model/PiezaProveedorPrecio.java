package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "PiezaProveedorPrecio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PiezaProveedorPrecio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pieza_id", nullable = false)
    private Pieza pieza;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "costo_unitario", nullable = false)
    private Double costoUnitario;

    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Column(name = "piezaSubajuste_id", nullable = true)
    private Long piezaSubajusteId;
    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) fechaRegistro = new Date();
    }
}
