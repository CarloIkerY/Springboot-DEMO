package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Evidencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evidencia_id;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private LocalDate fecha_subida;
}
