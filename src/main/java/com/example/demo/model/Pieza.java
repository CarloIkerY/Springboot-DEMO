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

    private String nombre;
    private Double costo_unitario;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @OneToMany(mappedBy = "pieza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PiezaAjuste> piezaAjuste;
}
