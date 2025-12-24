package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Actividad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actividad_id;

    @ManyToOne
    @JoinColumn(name = "subajuste_id", nullable = false)
    private Subajuste subajuste;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha_registro;
}
