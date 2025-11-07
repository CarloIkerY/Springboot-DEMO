package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Seguimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seguimiento_id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ajuste_id", nullable = false)
    private Ajuste ajuste;

    @ManyToOne
    @JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDate fecha_actualizacion;
}
