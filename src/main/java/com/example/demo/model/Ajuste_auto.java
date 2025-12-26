package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Ajuste_auto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ajuste_auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ajusteAuto_id;

    @ManyToOne
    @JoinColumn(name = "ajuste_id", nullable = false)
    private Ajuste ajuste;

    @ManyToOne
    @JoinColumn(name = "seguimiento_id", nullable = false)
    private Seguimiento seguimiento;

    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha_creacion;

    @Column(nullable = true)
    private LocalDateTime fecha_finalizado;

    @OneToMany(mappedBy = "ajusteAuto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subajuste> subajustes;
}
