package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Pieza")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pieza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pieza_id;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double costo_unitario;

    @OneToMany(mappedBy = "pieza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PiezaAjuste> piezasAjuste;
}
