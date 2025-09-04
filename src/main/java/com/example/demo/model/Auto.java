package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Auto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auto_id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 15)
    private String marca;

    @Column(nullable = false, length = 15)
    private String modelo;

    @Column(nullable = false, length = 4)
    private Integer anio;

    @Column(nullable = false, length = 15)
    private String placa;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cotizacion> cotizaciones;
}
