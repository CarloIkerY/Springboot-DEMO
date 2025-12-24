package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double costo_total;

    @Column(nullable = false)
    private String estatus;

    @OneToOne
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonBackReference
    private Orden orden;
}