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

    @ManyToOne
    @JoinColumn(name = "chofer_id", nullable = true)
    private Chofer chofer;

    @Column(nullable = false, length = 15)
    private String marca;

    @Column(nullable = false, length = 15)
    private String modelo;

    @Column(nullable = false, length = 4)
    private Integer anio;

    @Column(nullable = false, length = 50)
    private String placa;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = true, length = 50)
    private String numero_serie;

    @Column(nullable = true)
    private Boolean transmision;

    @Column(nullable = true, length = 10)
    private Integer kilometraje;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cotizacion> cotizaciones;

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Historial> historial;

    @OneToOne(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Condicion_auto condicion_auto;
}
